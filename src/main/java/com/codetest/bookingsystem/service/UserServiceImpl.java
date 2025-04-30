package com.codetest.bookingsystem.service;

import com.codetest.bookingsystem.controller.SessionContext;
import com.codetest.bookingsystem.dto.CommonResponse;
import com.codetest.bookingsystem.dto.PWChangeRequest;
import com.codetest.bookingsystem.dto.UserDTO;
import com.codetest.bookingsystem.enums.Role;
import com.codetest.bookingsystem.enums.Status;
import com.codetest.bookingsystem.exception.ResourceNotFoundException;
import com.codetest.bookingsystem.interfaces.UserService;
import com.codetest.bookingsystem.mapper.UserMapper;
import com.codetest.bookingsystem.model.User;
import com.codetest.bookingsystem.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;

	@Override
	@CachePut(value = "userCache", key = "#userDTO.id")
	public UserDTO createUser(SessionContext sessionContext, UserDTO userDTO) {
		User user = UserMapper.mapToUser(userDTO);
		if (user.getRole() == null) {
			user.setRole(Role.ADMIN);
		}
		User saveUser = userRepository.save(user);
		return UserMapper.mapToUserDTO(saveUser);
	}

	@Override
	@Cacheable(value = "userCache", key = "#id")
	public UserDTO getUserById(SessionContext sessionContext, Long id) {
		User user = userRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Employee Not Found with given id :" + id));
		return UserMapper.mapToUserDTO(user);

	}

	@Override
	public List<UserDTO> getAllUsers(SessionContext sessionContext) {
		List<User> employees = userRepository.findAll();
		return employees.stream().map(UserMapper::mapToUserDTO).collect(Collectors.toList());
	}

	@Override
	@CachePut(value = "userCache", key = "#userDTO.id")
	public UserDTO updateUser(SessionContext sessionContext, UserDTO userDTO) {
		User user = userRepository.findById(userDTO.getId())
				.orElseThrow(() -> new ResourceNotFoundException("Employee not found"));

		user.setUserName(userDTO.getUserName());
		user.setEmail(userDTO.getEmail());
		user.setAddress(userDTO.getAddress());
		user.setPhoneNo(userDTO.getPhoneNo());
		if (userDTO.getStatus() != null) {
			user.setStatus(userDTO.getStatus());
		}

		User updatedUser = userRepository.save(user);
		return UserMapper.mapToUserDTO(updatedUser);

	}

	@Override
	@CacheEvict(value = "userCache", key = "#id")
	public UserDTO deleteUser(SessionContext sessionContext, Long id) {
		User user = userRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Employee Not Found with given id :" + id));
		user.setStatus(Status.INVALID);
		User deleteUser = userRepository.save(user);
		return UserMapper.mapToUserDTO(deleteUser);
	}

	@Override
    public UserDetailsService userDetailsService(SessionContext sessionContext) {
        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                return userRepository.findByEmail(username).orElseThrow(() -> new ResourceNotFoundException("User Not Found with given username :" + username));
            }
        };
    }

	@Override
	public CommonResponse changePassword(SessionContext sessionContext, Long userId, PWChangeRequest pwChangeRequest) {
		try {
			User user = userRepository.findById(userId)
					.orElseThrow(() -> new ResourceNotFoundException("User Not Found with given id :" + userId));
			user.setPassword(pwChangeRequest.getNewPassword());
			userRepository.save(user);
			CommonResponse commonResponse = new CommonResponse();
			commonResponse.setSuccess(true);
			commonResponse.setSuccessMessage("Password Changed Successfully");
			return commonResponse;
		} catch (RuntimeException e) {
			throw new ResourceNotFoundException(e.getMessage());
		}
	}
}
