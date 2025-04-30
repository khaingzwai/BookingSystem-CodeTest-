package com.codetest.bookingsystem.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.FORBIDDEN)
public class ForbiddenException extends RuntimeException {

    // Constructor with a message
    public ForbiddenException(String message) {
        super(message);
    }

    // Optional: Constructor with both message and cause
    public ForbiddenException(String message, Throwable cause) {
        super(message, cause);
    }
}
