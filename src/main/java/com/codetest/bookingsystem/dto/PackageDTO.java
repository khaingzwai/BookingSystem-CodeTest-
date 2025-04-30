package com.codetest.bookingsystem.dto;

import com.codetest.bookingsystem.embedded.Money;
import com.codetest.bookingsystem.enums.Status;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;

@Data
public class PackageDTO implements Serializable {

    private Long id;

    @NotNull(message = "name must not null")
    @NotBlank(message = "name must not null")
    private String name;

    @NotNull(message = "noOfAvailableDays must not null")
    @Min(value = 1, message = "Number of available days must be at least 1")
    private Integer noOfAvailableDays;

    private Money costOfAmount;

    @NotNull(message = "noOfAvailableCredits must not null")
    @Min(value = 1, message = "Number of available days must be at least 1")
    private Integer noOfAvailableCredits;

    @NotNull(message = "countryDTO must not null")
    private CountryDTO countryDTO;

    private String description;

    private Status status;
}
