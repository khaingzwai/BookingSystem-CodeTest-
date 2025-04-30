package com.codetest.bookingsystem.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class JWTAuthenticationResponse implements Serializable {
    private String token;
}
