package com.codetest.bookingsystem.service;

import com.codetest.bookingsystem.common.CommonConstants;
import com.codetest.bookingsystem.controller.SessionContext;
import com.codetest.bookingsystem.controller.SessionUtil;
import com.codetest.bookingsystem.dto.*;
import com.codetest.bookingsystem.job.BookingExpirationJob;
import com.codetest.bookingsystem.enums.BookingStatus;
import com.codetest.bookingsystem.enums.UserPackageStatus;
import com.codetest.bookingsystem.interfaces.BookingService;
import com.codetest.bookingsystem.mapper.BookingMapper;
import com.codetest.bookingsystem.mapper.UserPackageMapper;
import com.codetest.bookingsystem.model.Booking;
import com.codetest.bookingsystem.model.ScheduleClass;
import com.codetest.bookingsystem.model.UserPackage;
import com.codetest.bookingsystem.repository.BookingRepository;
import com.codetest.bookingsystem.repository.ScheduleClassRepository;
import com.codetest.bookingsystem.repository.UserPackageRepository;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Calendar;
import java.util.stream.Collectors;

@Service
public class BookingServiceImpl implements BookingService {

	@Autowired
	private Scheduler scheduler;

	@Autowired
	private BookingRepository bookingRepository;

	@Autowired
	private UserPackageRepository userPackageRepository;

	@Autowired
	private ScheduleClassRepository scheduleClassRepository;

	@Override
	@CachePut(value = "booking", key = "#bookingRequest.userId")
	@Transactional
	public BookingResponse addBooking(SessionContext sessionContext, BookingRequest bookingRequest) {
		try {
			Long classId = bookingRequest.getClassId();
			Integer noOfSlots = bookingRequest.getNoOfSlots();

			UserPackage userPackage = userPackageRepository
					.findByIdWithStatus(sessionContext.getUserId(), UserPackageStatus.AVAILABLE).get();

			BookingResponse bookingResponse = new BookingResponse();
			ScheduleClass scheduleClass = scheduleClassRepository.findById(classId).get();
			boolean isTimeOk = isTimeConvenient(scheduleClass);
			if (!isTimeOk) {
				bookingResponse.setSuccess(false);
				bookingResponse.setMessage("Already Class");
			}
			if (!userPackage.getCountryId().equals(scheduleClass.getCountry().getId())) {
				bookingResponse.setSuccess(false);
				bookingResponse.setMessage("Your package country and your booking class country are difference");
				return bookingResponse;
			}
			if (userPackage.getNoOfRemainCredits() >= (scheduleClass.getNoOfCredits() * noOfSlots)) {
				userPackage.setNoOfRemainCredits(
						userPackage.getNoOfRemainCredits() - (scheduleClass.getNoOfCredits() * noOfSlots));
				userPackageRepository.save(userPackage);
			} else {
				bookingResponse.setSuccess(false);
				bookingResponse.setMessage("Your package not enough credits");
				return bookingResponse;
			}

			Booking booking = new Booking();
			booking.setUserId(userId);
			booking.setBookingReferenceNo(UUID.randomUUID().toString());
			booking.setScheduleClassId(classId);

			booking.setNoOfSlots(noOfSlots);

			if (Objects.equals(scheduleClass.getMaxSlots(), scheduleClass.getNoOfUsedSlots())) {
				booking.setBookingStatus(BookingStatus.PENDING);
				booking.setPendingDate(new Date());
			} else {
				booking.setBookingStatus(BookingStatus.CONFIRMED);
				booking.setBookingDate(new Date());

				scheduleClass.setNoOfUsedSlots(scheduleClass.getNoOfUsedSlots() + 1);
				scheduleClassRepository.save(scheduleClass);
			}
			Calendar expiredDate = Calendar.getInstance();
			expiredDate.setTime(scheduleClass.getClassDate());
			Calendar endTimeCalendar = Calendar.getInstance();
			endTimeCalendar.setTime(scheduleClass.getEndTime());
			expiredDate.set(Calendar.HOUR_OF_DAY, endTimeCalendar.get(Calendar.HOUR_OF_DAY));
			expiredDate.set(Calendar.MINUTE, endTimeCalendar.get(Calendar.MINUTE));
			expiredDate.set(Calendar.SECOND, endTimeCalendar.get(Calendar.SECOND));

			booking.setExpiryDate(expiredDate.getTime());
			booking.setCreationDate(new Date());

			booking = bookingRepository.save(booking);

			if (booking.getBookingStatus() == BookingStatus.PENDING) {

				// JobKey jobKey = new JobKey("job-" + booking.getId(), "group-" +
				// booking.getId());
				// String uniqueId = UUID.randomUUID().toString();
				// booking.getBookingReferenceNo() is UUID value
				JobDetail jobDetail = JobBuilder.newJob(BookingExpirationJob.class)
						.withIdentity("job-" + booking.getBookingReferenceNo())
						.usingJobData("bookingReferenceNo", booking.getBookingReferenceNo())
						.usingJobData("expirationDate", booking.getExpiryDate().getTime()).build();

				JobKey jobKey = jobDetail.getKey();

				Trigger trigger = TriggerBuilder.newTrigger().withIdentity("trigger-" + booking.getBookingReferenceNo())
						.startAt(booking.getExpiryDate()).build();

				scheduler.scheduleJob(jobDetail, trigger);

				booking.setJobId(jobKey.toString());
				bookingRepository.save(booking);
			}

			bookingResponse.setSuccess(true);
			bookingResponse.setMessage("Booking Success");

			return bookingResponse;
		} catch (RuntimeException | SchedulerException e) {
			throw new RuntimeException(e);
		}

	}

	public boolean isTimeConvenient(ScheduleClass newClass) {
		Date classDate = newClass.getClassDate();
		Date newStartTime = newClass.getStartTime();
		Date newEndTime = newClass.getEndTime();

		List<ScheduleClass> existingClasses = scheduleClassRepository.findByClassDate(classDate);

		for (ScheduleClass existingClass : existingClasses) {
			Date existingStartTime = existingClass.getStartTime();
			Date existingEndTime = existingClass.getEndTime();

			if ((newStartTime.before(existingEndTime) && newEndTime.after(existingStartTime))
					|| newStartTime.equals(existingStartTime) || newEndTime.equals(existingEndTime)) {
				return false;
			}
		}
		return true;
	}

	@Override
	public BookingResponse cancelBooking(SessionContext sessionContext, BookingCancel bookingCancel) {
		try {
			Booking booking = bookingRepository.findById(bookingCancel.getBookingId()).get();
			ScheduleClass scheduleClass = scheduleClassRepository.findById(booking.getScheduleClassId()).get();

			if (booking.getBookingStatus().equals(BookingStatus.PENDING)) {
				// JobKey jobKey = new JobKey(booking.getJobId(), "group-" + booking.getId());
				BookingJobExpiration(booking);
			}
			if (isRefund(scheduleClass)) {
				UserPackage userPackage = userPackageRepository.findById(bookingCancel.getUserId()).get();
				userPackage.setNoOfRemainCredits(
						userPackage.getNoOfRemainCredits() + (scheduleClass.getNoOfCredits() * booking.getNoOfSlots()));
				userPackageRepository.save(userPackage);
			}

			Pageable pageable = PageRequest.of(0, booking.getNoOfSlots());
			List<Booking> pendingBooking = bookingRepository.findTopPendingBooking(booking.getScheduleClassId(),
					BookingStatus.PENDING, pageable);
			Integer noOfPendingBooking = 0;
			for (Booking pendingBookingBooking : pendingBooking) {
				pendingBookingBooking.setBookingStatus(BookingStatus.CONFIRMED);
				pendingBookingBooking.setBookingDate(new Date());
				BookingJobExpiration(booking);
				noOfPendingBooking += pendingBookingBooking.getNoOfSlots();
				bookingRepository.save(pendingBookingBooking);
			}

			scheduleClass
					.setNoOfUsedSlots(scheduleClass.getNoOfUsedSlots() - booking.getNoOfSlots() + noOfPendingBooking);
			booking.setBookingStatus(BookingStatus.CANCELLED);
			booking.setCancelledDate(new Date());
			bookingRepository.save(booking);

		} catch (RuntimeException | SchedulerException e) {
			throw new RuntimeException(e);
		}
		return null;
	}

	private void BookingJobExpiration(Booking booking) throws SchedulerException {
		String[] jobKeyParts = booking.getJobId().split("@");
		if (jobKeyParts.length == 2) {
			JobKey jobKey = new JobKey(jobKeyParts[0], jobKeyParts[1]);
			// Delete the job
			boolean jobDeleted = scheduler.deleteJob(jobKey);

			if (jobDeleted) {
				System.out.println("Job successfully deleted.");
			} else {
				System.out.println("Job not found or could not be deleted.");
			}
		} else {
			System.out.println("Invalid job ID format.");
		}
	}

	@Override
	public List<BookingDTO> getAllBookingsByUserId(SessionContext sessionContext, Long userId) {
		try {
			Optional<List<Booking>> bookings = bookingRepository.findByUserId(userId);
			if (bookings.isPresent()) {
				List<BookingDTO> bookingDTOS = bookings.get().stream().map(BookingMapper::mapToDTO) // Convert each
																									// UserPackage to
																									// UserPackageDTO
						.toList();
				List<BookingDTO> resultBookingList = new ArrayList<>();
				if (!bookingDTOS.isEmpty()) {
					for (BookingDTO bookingDTO : bookingDTOS) {
						ScheduleClass scheduleClass = scheduleClassRepository.findById(bookingDTO.getScheduleClassId())
								.get();
						bookingDTO.setScheduleClassName(scheduleClass.getName());

						String classDateTimeRange = getString(scheduleClass);

						bookingDTO.setScheduleClassDescription(scheduleClass.getId() + " " + classDateTimeRange);
						resultBookingList.add(bookingDTO);
					}

				}
				return resultBookingList;
			} else {
				return new ArrayList<>();
			}
		} catch (RuntimeException e) {
			throw new RuntimeException(e);
		}
	}

	private static String getString(ScheduleClass scheduleClass) {
		Date classDate = scheduleClass.getClassDate();
		Date startTime = scheduleClass.getStartTime();
		Date endTime = scheduleClass.getEndTime();

		SimpleDateFormat dateFormatter = new SimpleDateFormat(CommonConstants.FULL_FORMAT_DATE);
		SimpleDateFormat timeFormatter = new SimpleDateFormat(CommonConstants.FULL_FORMAT_TIME);

		String formattedClassDate = dateFormatter.format(classDate);
		String formattedStartTime = timeFormatter.format(startTime);
		String formattedEndTime = timeFormatter.format(endTime);

		return formattedClassDate + " " + formattedStartTime + " - " + "(" + formattedEndTime + ")";
	}

	public boolean isRefund(ScheduleClass scheduleClass) {
		Date currentTime = new Date();

		Calendar classDateCal = Calendar.getInstance();
		classDateCal.setTime(scheduleClass.getClassDate());

		Calendar startTimeCal = Calendar.getInstance();
		startTimeCal.setTime(scheduleClass.getStartTime());

		classDateCal.set(Calendar.HOUR_OF_DAY, startTimeCal.get(Calendar.HOUR_OF_DAY));
		classDateCal.set(Calendar.MINUTE, startTimeCal.get(Calendar.MINUTE));
		classDateCal.set(Calendar.SECOND, startTimeCal.get(Calendar.SECOND));

		Date combinedStartTime = classDateCal.getTime();

		long timeDifference = combinedStartTime.getTime() - currentTime.getTime();
		long hoursDifference = timeDifference / (1000 * 60 * 60);

		return hoursDifference >= 4;
	}

}
