package com.codetest.bookingsystem.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class PurchaseResponseDTO implements Serializable {
    private boolean success;
    private String message;
}
