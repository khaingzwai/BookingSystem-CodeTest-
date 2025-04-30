package com.codetest.bookingsystem.mapper;

import com.codetest.bookingsystem.dto.UserPackageDTO;
import com.codetest.bookingsystem.model.UserPackage;

public class UserPackageMapper {

    public static UserPackageDTO toDTO(UserPackage userPackage) {
        UserPackageDTO userPackageDTO = null;
        if (userPackage != null) {
            userPackageDTO = new UserPackageDTO();
            userPackageDTO.setId(userPackage.getId());
            userPackageDTO.setPackageName(userPackage.getPackageName());
            userPackageDTO.setExpireDate(userPackage.getExpireDate());
            userPackageDTO.setCostOfAmount(userPackage.getCostOfAmount());
            userPackageDTO.setNoOfCredits(userPackage.getNoOfCredits());
            userPackageDTO.setNoOfRemainCredits(userPackage.getNoOfRemainCredits());
            userPackageDTO.setUserPackageStatus(userPackage.getUserPackageStatus());
            userPackageDTO.setCountryId(userPackage.getCountryId());
            userPackageDTO.setUserId(userPackage.getUserId());
            userPackageDTO.setPackageId(userPackage.getPackageId());
        }
        return userPackageDTO;
    }

    public static UserPackage toModel(UserPackageDTO userPackageDTO) {
        UserPackage userPackage = null;
        if (userPackageDTO != null) {
            userPackage = new UserPackage();
            userPackage.setId(userPackageDTO.getId());
            userPackage.setPackageName(userPackageDTO.getPackageName());
            userPackage.setExpireDate(userPackageDTO.getExpireDate());
            userPackage.setCostOfAmount(userPackageDTO.getCostOfAmount());
            userPackage.setNoOfCredits(userPackageDTO.getNoOfCredits());
            userPackage.setNoOfRemainCredits(userPackageDTO.getNoOfRemainCredits());
            userPackage.setUserPackageStatus(userPackageDTO.getUserPackageStatus());
            userPackage.setCountryId(userPackageDTO.getCountryId());
            userPackage.setUserId(userPackageDTO.getUserId());
            userPackage.setPackageId(userPackageDTO.getPackageId());
        }

        return userPackage;
    }
}
