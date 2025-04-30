package com.codetest.bookingsystem.controller.user;

import com.codetest.bookingsystem.controller.SessionUtil;
import com.codetest.bookingsystem.dto.*;
import com.codetest.bookingsystem.enums.Status;
import com.codetest.bookingsystem.interfaces.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/user")
@RequiredArgsConstructor
@Tag(name = "User Profile", description = "Operations related to user profiles and roles")
public class UserProfileController {

	private final UserPackageService userPackageService;

	private final PackageService packageService;

	private final BookingService bookingService;

	private final ScheduleClassService scheduleClassService;

	private final UserService userService;

	@Operation(summary = "Purchase Package", description = "Package Purchase For User")
	@PostMapping("{userid}/purchasepackage")
	public ResponseEntity<PurchaseResponseDTO> purchasePackage(@PathVariable("userid") Long id,
			@Valid @RequestBody PurchaseUserPackageDTO purchaseUserPackageDTO) {
		try {
			PurchaseResponseDTO purchaseResponseDTO = userPackageService
					.purchaseUserPackage(SessionUtil.getCurrentSession(), id, purchaseUserPackageDTO);
			return ResponseEntity.ok(purchaseResponseDTO);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // Respond with 500 status
		}
	}

	@Operation(summary = "UserPackage List", description = "Retrieve Own User Package by ID")
	@GetMapping("{userid}/packages")
	public ResponseEntity<List<UserPackageDTO>> packageList(@PathVariable("userid") Long id) {
		try {
			List<UserPackageDTO> userPackageDTOS = userPackageService
					.getAllUserPackagesByUserId(SessionUtil.getCurrentSession(), id);
			return ResponseEntity.ok(userPackageDTOS);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // Respond with 500 status
		}
	}

	@Operation(summary = "Choose Package List", description = "Retrieve Package List")
	@GetMapping("packages")
	public ResponseEntity<List<PackageDTO>> packageList() {
		try {
			List<PackageDTO> packageDTOS = packageService.getPackagesByStatus(SessionUtil.getCurrentSession(),
					Status.VALID);
			return ResponseEntity.ok(packageDTOS);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // Respond with 500 status
		}
	}

	@Operation(summary = "Booking Cancel", description = "Retrieve the profile information of a user by ID")
	@PutMapping("{userid}/booking/cancel")
	public ResponseEntity<BookingResponse> cancelBooking(@PathVariable("userid") Long id,
			@RequestBody BookingCancel bookingCancel) {
		try {
			if (bookingCancel.getBookingId() == null) {
				bookingCancel.setBookingId(id);
			}
			BookingResponse bookingResponse = bookingService.cancelBooking(SessionUtil.getCurrentSession(),
					bookingCancel);
			return ResponseEntity.ok(bookingResponse);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // Respond with 500 status
		}
	}

	@Operation(summary = "Booking Create", description = "Booking Create")
	@PostMapping("{userid}/booking")
	public ResponseEntity<BookingResponse> addBooking(@PathVariable("userid") Long id,
			@Valid @RequestBody BookingRequest bookingRequest) {
		try {
			if (bookingRequest.getUserId() != null) {
				bookingRequest.setUserId(id);
			}
			BookingResponse bookingResponse = bookingService.addBooking(SessionUtil.getCurrentSession(),
					bookingRequest);
			return ResponseEntity.ok(bookingResponse);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // Respond with 500 status
		}
	}

	@Operation(summary = "Choose Class Schedule List", description = "Retrieve all Schedule List")
	@GetMapping("schedulelist")
	public ResponseEntity<List<ScheduleClassDTO>> scheduleList() {
		try {
			List<ScheduleClassDTO> SheduleClassDTOs = scheduleClassService.findUpcomingClasses(SessionUtil.getCurrentSession());
			return ResponseEntity.ok(SheduleClassDTOs);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	@Operation(summary = "User Class Schedule List", description = "Retrieve User's Class Schedule List")
	@GetMapping("/{userid}/bookingclass")
	public ResponseEntity<List<BookingDTO>> userScheduleList(@PathVariable("userid") Long userId) {
		try {
			List<BookingDTO> bookingDTOs = bookingService.getAllBookingsByUserId(SessionUtil.getCurrentSession(),
					userId);
			return ResponseEntity.ok(bookingDTOs);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	@Operation(summary = "Change User Password", description = "Change User Password")
	@PostMapping("{userid}/changePassword")
	public ResponseEntity<CommonResponse> changePassword(@PathVariable("userid") Long userId,
			PWChangeRequest pwChangeRequest) {
		try {
			CommonResponse successResponse = userService.changePassword(SessionUtil.getCurrentSession(), userId,
					pwChangeRequest);
			return ResponseEntity.ok(successResponse);
		} catch (RuntimeException e) {
			throw new RuntimeException(e);
		}
	}

}
