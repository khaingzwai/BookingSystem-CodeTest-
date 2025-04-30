package com.codetest.bookingsystem.interfaces;

import com.codetest.bookingsystem.controller.SessionContext;
import com.codetest.bookingsystem.dto.CommonResponse;
import com.codetest.bookingsystem.dto.PWChangeRequest;
import com.codetest.bookingsystem.dto.UserDTO;
import com.codetest.bookingsystem.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService {
	UserDTO createUser(SessionContext sessionContext, UserDTO userDTO);

	UserDTO getUserById(SessionContext sessionContext, Long id);

	List<UserDTO> getAllUsers(SessionContext sessionContext);

	UserDTO updateUser(SessionContext sessionContext, UserDTO userDTO);

	UserDTO deleteUser(SessionContext sessionContext, Long id);

	UserDetailsService userDetailsService(SessionContext sessionContext);

	CommonResponse changePassword(SessionContext sessionContext, Long userId, PWChangeRequest pwChangeRequest);
}
