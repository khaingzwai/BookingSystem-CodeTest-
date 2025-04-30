package com.codetest.bookingsystem.mapper;

import com.codetest.bookingsystem.dto.CountryDTO;
import com.codetest.bookingsystem.model.Country;

public class CountryMapper {

    public static CountryDTO mapToDTO(Country country) {
        CountryDTO dto = null;
        if (country != null) {
            dto = new CountryDTO();
            dto.setId(country.getId());
            dto.setName(country.getName());
            dto.setCode(country.getCode());
            dto.setStatus(country.getStatus());
        }
        return dto;
    }

    public static Country mapToModel(CountryDTO countryDTO) {
        Country model = null;
        if (countryDTO != null) {
            model = new Country();
            model.setId(countryDTO.getId());
            model.setName(countryDTO.getName());
            model.setCode(countryDTO.getCode());
            model.setStatus(countryDTO.getStatus());
        }
        return model;
    }
}
