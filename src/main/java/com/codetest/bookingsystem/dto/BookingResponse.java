package com.codetest.bookingsystem.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class BookingResponse implements Serializable {

    private boolean success;
    private String message;
}
