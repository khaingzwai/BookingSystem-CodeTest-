package com.codetest.bookingsystem.job;

import com.codetest.bookingsystem.enums.BookingStatus;
import com.codetest.bookingsystem.enums.UserPackageStatus;
import com.codetest.bookingsystem.model.Booking;
import com.codetest.bookingsystem.model.UserPackage;
import com.codetest.bookingsystem.repository.BookingRepository;
import com.codetest.bookingsystem.repository.UserPackageRepository;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class BookingExpirationJob implements Job {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    UserPackageRepository userPackageRepository;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        String bookingReferenceNo = context.getJobDetail().getJobDataMap().getString("bookingReferenceNo");
        Date expirationDate = (Date) context.getJobDetail().getJobDataMap().get("expirationDate");

        Booking booking = bookingRepository.findByBookingReferenceNo(bookingReferenceNo);
        if (booking != null && expirationDate != null && expirationDate.before(new Date())) {
            booking.setBookingStatus(BookingStatus.REFUND);
            bookingRepository.save(booking);
            UserPackage userPackage = userPackageRepository.findByUserIdAndStatus(booking.getUserId(), UserPackageStatus.AVAILABLE);
            userPackage.setNoOfRemainCredits(userPackage.getNoOfRemainCredits() + booking.getNoOfSlots());
            System.out.println("Booking ID " + bookingReferenceNo + " has refund and status updated.");
        }
    }
}