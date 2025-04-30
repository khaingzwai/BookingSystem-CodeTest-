package com.codetest.bookingsystem.dto;

import com.codetest.bookingsystem.enums.Role;
import com.codetest.bookingsystem.enums.Status;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class RegisterRequest implements Serializable {

    private String userName;

    @NotNull(message = "email must not null")
    @NotBlank(message = "email must not null")
    @Pattern(regexp = "^[A-Za-z0-9+_.-]+@(.+)$", message = "Invalid email format")
    private String email;

    @NotNull(message = "password must not null")
    @NotBlank(message = "password must not null")
    @Size(min = 5,max = 20, message = "password must be between 5 and 20 character")
    private String password;

    private String phoneNo;
    private String address;
}
