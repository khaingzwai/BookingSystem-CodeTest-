package com.codetest.bookingsystem.controller;

import com.codetest.bookingsystem.dto.JWTAuthenticationResponse;
import com.codetest.bookingsystem.dto.LoginRequest;
import com.codetest.bookingsystem.dto.RegisterRequest;
import com.codetest.bookingsystem.dto.UserDTO;
import com.codetest.bookingsystem.exception.UserNotFoundException;
import com.codetest.bookingsystem.interfaces.AuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.hibernate.service.spi.ServiceException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication Profile", description = "User Login and Sign Up")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @Operation(summary = "Sign Up", description = "Sign Up")
    @PostMapping("register")
    public ResponseEntity<UserDTO> signup(@Valid @RequestBody RegisterRequest registerRequest) {
        try {
            UserDTO userDTO = authenticationService.register(registerRequest);
            return ResponseEntity.ok(userDTO);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Operation(summary = "Sign In", description = "Sign In")
    @PostMapping("login")
    public ResponseEntity<?> signIn(@Valid @RequestBody LoginRequest loginRequest) {
        try {
            JWTAuthenticationResponse response = authenticationService.login(loginRequest);
            if (response != null) {
                return ResponseEntity.status(HttpStatus.OK).body(response);
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials.");
            }
        } catch (ServiceException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.getMessage());
        } catch (UserNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }
}
