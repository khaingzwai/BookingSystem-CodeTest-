package com.codetest.bookingsystem.dto;

import com.codetest.bookingsystem.embedded.Money;
import com.codetest.bookingsystem.enums.UserPackageStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class UserPackageDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;

    private String packageName;

    private Date expireDate;

    private Money costOfAmount;

    private Integer noOfCredits;

    private Integer noOfRemainCredits;

    private UserPackageStatus userPackageStatus;

    private Long packageId;
    private Long countryId;
    private Long userId;
}
