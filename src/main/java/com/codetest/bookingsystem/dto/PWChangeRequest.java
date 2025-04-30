package com.codetest.bookingsystem.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class PWChangeRequest implements Serializable {
    private String newPassword;
}
