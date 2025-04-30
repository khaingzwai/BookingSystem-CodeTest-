package com.codetest.bookingsystem.service;

import com.codetest.bookingsystem.dto.JWTAuthenticationResponse;
import com.codetest.bookingsystem.dto.LoginRequest;
import com.codetest.bookingsystem.dto.RegisterRequest;
import com.codetest.bookingsystem.dto.UserDTO;
import com.codetest.bookingsystem.enums.Role;
import com.codetest.bookingsystem.enums.Status;
import com.codetest.bookingsystem.interfaces.AuthenticationService;
import com.codetest.bookingsystem.interfaces.JWTService;
import com.codetest.bookingsystem.mapper.UserMapper;
import com.codetest.bookingsystem.model.User;
import com.codetest.bookingsystem.repository.UserRepository;
import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JWTService jwtService;

    @Override
    @CachePut(value = "userRegister", key = "#registerRequest.email")
    public UserDTO register(RegisterRequest registerRequest) throws ServiceException {
        try {
            boolean isValid = isEmailValid("test@example.com");
            if (!isValid) {
                throw new ServiceException("Email is not valid");
            }
            User user = new User();
            user.setEmail(registerRequest.getEmail());
            user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
            user.setUserName(registerRequest.getUserName());
            user.setPhoneNo(registerRequest.getPhoneNo());
            user.setAddress(registerRequest.getAddress());
            user.setRole(Role.USER);
            user.setStatus(Status.VALID);
            User saveUser = userRepository.save(user);
            return UserMapper.mapToUserDTO(saveUser);
        } catch (ServiceException e) {
            throw new ServiceException("Email cannot be null or empty");
        }

    }

    @Override
    @CachePut(value = "userRegister", key = "#loginRequest.email")
    public JWTAuthenticationResponse login(LoginRequest loginRequest) throws ServiceException {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword())
        );

        User user = userRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("Invalid email or password"));
        String jwt = jwtService.generateToken(user);
        JWTAuthenticationResponse jwtAuthenticationResponse = new JWTAuthenticationResponse();
        jwtAuthenticationResponse.setToken(jwt);

        //String refreshToken =jwtService.generateRefreshToken(new HashMap<>(), user);
        // jwtAuthenticationResponse.setRefreshToken(refreshToken);
        return jwtAuthenticationResponse;
    }

    @Override
    public boolean isEmailValid(String email) throws ServiceException {
        if (email == null || email.isEmpty()) {
            throw new ServiceException("Email cannot be null or empty");
        }

        String emailRegex = "^[\\w.%+-]+@[\\w.-]+\\.[a-zA-Z]{2,6}$";

        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }


}
