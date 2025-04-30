package com.codetest.bookingsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponse implements Serializable {
    private String message;
    private HttpStatus status;
    private String path;
    private Date timestamp;
}
