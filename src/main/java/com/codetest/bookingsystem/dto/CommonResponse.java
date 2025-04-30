package com.codetest.bookingsystem.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class CommonResponse implements Serializable {
    boolean success;
    String successMessage;
}
