package com.codetest.bookingsystem.controller;

import com.codetest.bookingsystem.dto.PackageDTO;
import com.codetest.bookingsystem.enums.Status;
import com.codetest.bookingsystem.exception.ForbiddenException;
import com.codetest.bookingsystem.interfaces.PackageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/admin/packages")
@RequiredArgsConstructor
@Tag(name = "Admin Profile", description = "Booking System Management")
public class PackagesController {

	private final PackageService packageService;

	@Operation(summary = "Create Package", description = "Create Package")
	@PostMapping
	public ResponseEntity<PackageDTO> createPackages(@Valid @RequestBody PackageDTO packageDTO) {
		try {
			PackageDTO createdPackages = packageService.createPackage(SessionUtil.getCurrentSession(), packageDTO);
			return ResponseEntity.ok(createdPackages);
		} catch (ForbiddenException f) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(packageDTO);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // Respond with 500 status
		}
	}

	@Operation(summary = "Update Package", description = "Update Package by Id")
	@PutMapping("{id}")
	public ResponseEntity<PackageDTO> updatePackage(@PathVariable Long id, @Valid @RequestBody PackageDTO packageDTO) {
		try {
			PackageDTO updatePackageDTO = packageService.updatePackage(SessionUtil.getCurrentSession(), id, packageDTO);
			return ResponseEntity.ok(updatePackageDTO);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // Respond with 500 status
		}
	}

	@Operation(summary = "Retrieve Package List ", description = "Retrieve Package List By Country Id")
	@GetMapping("packages")
	public ResponseEntity<List<PackageDTO>> getPackageListByCountryId(@RequestParam("countryId") Long countryId) {
		try {
			List<PackageDTO> packageDTOS = packageService.getPackagesByCountryAndStatus(SessionUtil.getCurrentSession(),
					countryId, Status.VALID);
			return ResponseEntity.ok(packageDTOS);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // Respond with 500 status
		}
	}

	@Operation(summary = "Retrieve Package", description = "Retrieve Package by Id")
	@GetMapping("{id}")
	public ResponseEntity<PackageDTO> getPackagesById(@PathVariable("id") Long id) {
		try {
			PackageDTO packageDTO = packageService.getPackageById(SessionUtil.getCurrentSession(), id);
			return ResponseEntity.ok(packageDTO);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // Respond with 500 status
		}
	}
}
