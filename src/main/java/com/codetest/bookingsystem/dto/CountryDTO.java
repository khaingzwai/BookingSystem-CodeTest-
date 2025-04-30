package com.codetest.bookingsystem.dto;

import com.codetest.bookingsystem.enums.Status;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CountryDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private Long id;

    @NotNull(message = "email must not null")
    @NotBlank(message = "email must not null")
    private String name;

    @NotNull(message = "email must not null")
    @NotBlank(message = "email must not null")
    private String code;

    private Status status;
}
