package com.codetest.bookingsystem.mapper;

import com.codetest.bookingsystem.dto.CountryDTO;
import com.codetest.bookingsystem.dto.PackageDTO;
import com.codetest.bookingsystem.model.Country;
import com.codetest.bookingsystem.model.Packages;

public class PackageMapper {
    public static PackageDTO toDTO(Packages packageEntity) {
        PackageDTO packageDTO = null;
        if (packageEntity != null) {
            packageDTO = new PackageDTO();
            packageDTO.setId(packageEntity.getId());
            packageDTO.setName(packageEntity.getName());
            packageDTO.setDescription(packageEntity.getDescription());
            packageDTO.setCostOfAmount(packageEntity.getCostOfAmount());
            packageDTO.setNoOfAvailableDays(packageEntity.getNoOfAvailableDays());
            packageDTO.setNoOfAvailableCredits(packageEntity.getNoOfAvailableCredits());
            packageDTO.setStatus(packageEntity.getStatus());

            CountryDTO countryDTO = new CountryDTO();
            if (packageEntity.getCountry() != null) {
                countryDTO.setId(packageEntity.getCountry().getId());
                countryDTO.setName(packageEntity.getCountry().getName());
                countryDTO.setCode(packageEntity.getCountry().getCode());
                countryDTO.setStatus(packageEntity.getCountry().getStatus());
                packageDTO.setCountryDTO(countryDTO);
            }

        }
        return packageDTO;
    }

    // Convert PackageDTO to Package entity
    public static Packages toEntity(PackageDTO packageDTO) {
        Packages packages = null;
        if (packageDTO != null) {
            packages = new Packages();
            packages.setId(packageDTO.getId());
            packages.setName(packageDTO.getName());
            packages.setDescription(packageDTO.getDescription());
            packages.setCostOfAmount(packageDTO.getCostOfAmount());
            packages.setNoOfAvailableCredits(packageDTO.getNoOfAvailableCredits());
            packages.setNoOfAvailableDays(packageDTO.getNoOfAvailableDays());
            packages.setStatus(packageDTO.getStatus());
            Country country = new Country();
            if (packageDTO.getCountryDTO() != null) {
                country.setId(packageDTO.getCountryDTO().getId());
                country.setName(packageDTO.getCountryDTO().getName());
                country.setCode(packageDTO.getCountryDTO().getCode());
                country.setStatus(packageDTO.getCountryDTO().getStatus());
                packages.setCountry(country);
            }

        }
        return packages;
    }
}
