package com.codetest.bookingsystem.controller;

import com.codetest.bookingsystem.dto.CountryDTO;
import com.codetest.bookingsystem.exception.ForbiddenException;
import com.codetest.bookingsystem.interfaces.CountryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/admin/country")
@RequiredArgsConstructor
@Tag(name = "Admin Profile", description = "Booking System Management")
public class CountryController {

	private final CountryService countryService;

	@Operation(summary = "Create Company", description = "Create Company")
	@PostMapping
	public ResponseEntity<CountryDTO> createCountry(@Valid @RequestBody CountryDTO countryDTO) {
		try {
			SessionContext sessionContext = SessionUtil.getCurrentSession();

			CountryDTO createdCountry = countryService.createCountry(SessionUtil.getCurrentSession(), countryDTO);
			return ResponseEntity.ok(createdCountry);
		} catch (ForbiddenException f) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(countryDTO);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // Respond with 500 status
		}
	}

	@Operation(summary = "Update Company", description = "Update Company")
	@PutMapping("/{id}")
	public ResponseEntity<CountryDTO> updateCountry(@PathVariable Long id, @Valid @RequestBody CountryDTO countryDTO) {
		try {
			CountryDTO updatedCountry = countryService.updateCountry(SessionUtil.getCurrentSession(), countryDTO, id);
			return ResponseEntity.ok(updatedCountry);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // Respond with 500 status
		}
	}

	@Operation(summary = "Company List", description = "Company List")
	@GetMapping
	public ResponseEntity<List<CountryDTO>> countryList() {
		try {
			List<CountryDTO> countryList = countryService.getAllCountries(SessionUtil.getCurrentSession());
			return ResponseEntity.ok(countryList);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // Respond with 500 status
		}
	}

	@Operation(summary = "Retrieve Country", description = "Retrieve Country By Id")
	@GetMapping("/{id}")
	public ResponseEntity<CountryDTO> getCountryById(@PathVariable("id") Long id) {
		try {
			CountryDTO countryDTO = countryService.getCountryById(SessionUtil.getCurrentSession(), id);
			return ResponseEntity.ok(countryDTO);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // Respond with 500 status
		}
	}

}
