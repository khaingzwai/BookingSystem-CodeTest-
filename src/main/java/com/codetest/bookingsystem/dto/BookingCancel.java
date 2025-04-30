package com.codetest.bookingsystem.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class BookingCancel implements Serializable {
    private Long userId;
    private Long bookingId;
}
