package com.codetest.bookingsystem.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class BookingRequest implements Serializable{

    private Long classId;
    private Integer noOfSlots;
}
