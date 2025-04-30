package com.codetest.bookingsystem.service;

import com.codetest.bookingsystem.controller.SessionContext;
import com.codetest.bookingsystem.dto.CountryDTO;
import com.codetest.bookingsystem.enums.Status;
import com.codetest.bookingsystem.exception.ResourceNotFoundException;
import com.codetest.bookingsystem.interfaces.CountryService;
import com.codetest.bookingsystem.mapper.CountryMapper;
import com.codetest.bookingsystem.model.Country;
import com.codetest.bookingsystem.repository.CountryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CountryServiceImpl implements CountryService {

	@Autowired
	private CountryRepository countryRepository;

	@Override
	public CountryDTO createCountry(SessionContext sessionContext, CountryDTO countryDTO) throws Exception {
		try {
			Country country = CountryMapper.mapToModel(countryDTO);
			country.setStatus(Status.VALID);
			country.setCreatedBy(sessionContext.getUserId());
			country = countryRepository.save(country);
			return CountryMapper.mapToDTO(country);
		} catch (RuntimeException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public CountryDTO getCountryById(SessionContext sessionContext, Long countryId) throws Exception {
		try {
			Country country = countryRepository.findById(countryId)
					.orElseThrow(() -> new ResourceNotFoundException("Country Not Found with given id :" + countryId));
			return CountryMapper.mapToDTO(country);
		} catch (RuntimeException e) {
			throw new RuntimeException(e);
		}

	}

	@Override
	public List<CountryDTO> getAllCountries(SessionContext sessionContext) throws Exception {
		List<Country> countryDTOS = countryRepository.findAll();
		return countryDTOS.stream().map(CountryMapper::mapToDTO).collect(Collectors.toList());
	}

	@Override
	public CountryDTO updateCountry(SessionContext sessionContext, CountryDTO countryDTO, Long countryId)
			throws Exception {
		Country country = countryRepository.findById(countryId).orElse(null);
		if (country == null) {
			throw new ResourceNotFoundException("Country Not Found with given id :" + countryId);
		}
		country.setName(countryDTO.getName());
		if (countryDTO.getStatus() != null) {
			country.setStatus(countryDTO.getStatus());
		}
		country = countryRepository.save(country);
		return CountryMapper.mapToDTO(country);
	}
}
