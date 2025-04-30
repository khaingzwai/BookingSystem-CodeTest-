package com.codetest.bookingsystem.controller;

import com.codetest.bookingsystem.dto.ScheduleClassDTO;
import com.codetest.bookingsystem.exception.ForbiddenException;
import com.codetest.bookingsystem.interfaces.ScheduleClassService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/admin/scheduleclass")
@RequiredArgsConstructor
@Tag(name = "Admin Profile", description = "Booking System Management")
public class SchedulerClassController {

	private final ScheduleClassService scheduleClassService;

	@Operation(summary = "Retrieve Schedule Class", description = "Retrieve Schedule Class")
	@GetMapping
	public ResponseEntity<List<ScheduleClassDTO>> getScheduleClasses() {
		try {
			List<ScheduleClassDTO> scheduleList = scheduleClassService
					.getAllScheduleClass(SessionUtil.getCurrentSession());
			return ResponseEntity.ok(scheduleList);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // Respond with 500 status
		}
	}

	@Operation(summary = "Create Schedule Class", description = "Create Schedule Class")
	@PostMapping
	public ResponseEntity<ScheduleClassDTO> createSchedule(@Valid @RequestBody ScheduleClassDTO scheduleClassDTO) {
		try {
			ScheduleClassDTO scheduleClassDTO1 = scheduleClassService
					.createScheduleClass(SessionUtil.getCurrentSession(), scheduleClassDTO);
			return ResponseEntity.ok(scheduleClassDTO1);
		} catch (ForbiddenException f) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(scheduleClassDTO);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // Respond with 500 status
		}
	}

	@Operation(summary = "Update Schedule Class", description = "Update Schedule Class")
	@PutMapping("{id}")
	public ResponseEntity<ScheduleClassDTO> updateSchedule(@PathVariable Long id,
			@Valid @RequestBody ScheduleClassDTO scheduleClassDTO) {
		try {
			ScheduleClassDTO updateScheduleClassDTO = scheduleClassService
					.updateScheduleClass(SessionUtil.getCurrentSession(), id, scheduleClassDTO);
			return ResponseEntity.ok(updateScheduleClassDTO);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // Respond with 500 status
		}
	}

	@Operation(summary = "Retrieve Schedule Class", description = "Retrieve Schedule Class by Id")
	@GetMapping("{id}")
	public ResponseEntity<ScheduleClassDTO> getScheduleClassById(@PathVariable("id") Long id) {
		try {
			ScheduleClassDTO scheduleClassDTO = scheduleClassService
					.getScheduleClassById(SessionUtil.getCurrentSession(), id);
			return ResponseEntity.ok(scheduleClassDTO);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // Respond with 500 status
		}
	}
}
