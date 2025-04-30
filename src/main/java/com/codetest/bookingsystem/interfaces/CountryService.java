package com.codetest.bookingsystem.interfaces;

import com.codetest.bookingsystem.controller.SessionContext;
import com.codetest.bookingsystem.dto.CountryDTO;

import java.util.List;

public interface CountryService {

	public CountryDTO createCountry(SessionContext sessionContext, CountryDTO countryDTO) throws Exception;

	public CountryDTO getCountryById(SessionContext sessionContext, Long countryId) throws Exception;

	public List<CountryDTO> getAllCountries(SessionContext sessionContext) throws Exception;

	public CountryDTO updateCountry(SessionContext sessionContext, CountryDTO countryDTO, Long countryId)
			throws Exception;
}
