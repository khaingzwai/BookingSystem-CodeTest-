package com.codetest.bookingsystem.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class LoginRequest implements Serializable {

    @NotNull(message = "email must not null")
    @NotBlank(message = "email must not null")
    private String email;

    @NotNull(message = "password must not null")
    @NotBlank(message = "password must not null")
    private String password;
}
