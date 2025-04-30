package com.codetest.bookingsystem.dto;

import com.codetest.bookingsystem.common.CommonConstants;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalTime;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleClassDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;

    private String name;
    private Integer maxSlots;

    private Integer noOfUsedSlots;

    @JsonFormat(pattern = CommonConstants.FULL_FORMAT_DATE)
    private Date classDate;

    @JsonFormat(pattern = CommonConstants.FULL_FORMAT_TIME)
    private Date startTime;  // Change to LocalTime

    @JsonFormat(pattern = CommonConstants.FULL_FORMAT_TIME)
    private Date endTime;

    private String address;
    private String lecturerName;
    private String phNo;
    private Integer noOfCredits;
    private String description;

    private CountryDTO countryDTO;
}
