package com.codetest.bookingsystem.interfaces;

import com.codetest.bookingsystem.dto.JWTAuthenticationResponse;
import com.codetest.bookingsystem.dto.LoginRequest;
import com.codetest.bookingsystem.dto.RegisterRequest;
import com.codetest.bookingsystem.dto.UserDTO;
import org.hibernate.service.spi.ServiceException;

public interface AuthenticationService {

    UserDTO register(RegisterRequest registerRequest) throws ServiceException;

    JWTAuthenticationResponse login(LoginRequest loginRequest) throws ServiceException;

    boolean isEmailValid(String email) throws ServiceException;
}
