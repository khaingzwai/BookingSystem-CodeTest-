package com.codetest.bookingsystem.dto;

import com.codetest.bookingsystem.common.CommonConstants;
import com.codetest.bookingsystem.enums.BookingStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class BookingDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;

    private String bookingReferenceNo;

    private Integer noOfSlots;

    private BookingStatus bookingStatus;

    @JsonFormat(pattern = CommonConstants.FULL_FORMAT_DATE_TIME)
    private Date bookingDate;

    @JsonFormat(pattern = CommonConstants.FULL_FORMAT_DATE_TIME)
    private Date pendingDate;

    @JsonFormat(pattern = CommonConstants.FULL_FORMAT_DATE_TIME)
    private Date refundDate;

    @JsonFormat(pattern = CommonConstants.FULL_FORMAT_DATE_TIME)
    private Date cancelledDate;

    private Long scheduleClassId;

    private String scheduleClassName;

    private String scheduleClassDescription;

    private Long userId;

    private String jobId;

    @JsonFormat(pattern = CommonConstants.FULL_FORMAT_DATE_TIME)
    private Date expiryDate;

    @JsonFormat(pattern = CommonConstants.FULL_FORMAT_DATE_TIME)
    private Date creationDate;

}
