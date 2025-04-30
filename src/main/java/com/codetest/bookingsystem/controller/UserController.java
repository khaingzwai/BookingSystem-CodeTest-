package com.codetest.bookingsystem.controller;

import com.codetest.bookingsystem.dto.UserDTO;
import com.codetest.bookingsystem.interfaces.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("api/admin/user")
@Tag(name = "Admin Profile", description = "Booking System Management")
public class UserController {

	@Autowired
	private UserService userService;

	@Operation(summary = "create", description = "createUser")
	@PostMapping
	public ResponseEntity<UserDTO> createUser(@Valid @RequestBody UserDTO userDTO) {
		UserDTO registerUser = userService.createUser(SessionUtil.getCurrentSession(), userDTO);
		return new ResponseEntity<>(registerUser, HttpStatus.CREATED);
	}

	@Operation(summary = "getUserById", description = "get User By Id")
	@GetMapping("{id}")
	public ResponseEntity<UserDTO> getUserById(@PathVariable("id") Long id) {
		UserDTO userDTO = userService.getUserById(SessionUtil.getCurrentSession(), id);
		return ResponseEntity.ok(userDTO);
	}

	@GetMapping
	@Operation(summary = "getAllUsers", description = "get All Users")
	public ResponseEntity<List<UserDTO>> getAllUsers() {
		List<UserDTO> userDTOList = userService.getAllUsers(SessionUtil.getCurrentSession(),);
		return ResponseEntity.ok(userDTOList);
	}

	@PutMapping("update")
	@Operation(summary = "updateUser", description = "updateUser")
	public ResponseEntity<UserDTO> updateUser(@RequestBody UserDTO userDTO) {
		UserDTO updatedUserDTO = userService.updateUser(SessionUtil.getCurrentSession(), userDTO);
		return ResponseEntity.ok(updatedUserDTO);
	}

	@DeleteMapping("delete/{id}")
	@Operation(summary = "deleteEmployeeById", description = "delete Employee By Id")
	public ResponseEntity<String> deleteEmployeeById(@PathVariable("id") Long id) {
		userService.deleteUser(SessionUtil.getCurrentSession(), id);
		return ResponseEntity.ok("User deleted");
	}

}
