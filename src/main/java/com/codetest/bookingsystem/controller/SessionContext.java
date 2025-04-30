package com.codetest.bookingsystem.controller;

import com.codetest.bookingsystem.enums.Role;

import lombok.Data;

@Data
public class SessionContext {

	private Long userId;
	private String userName;
	private Role role;

	public SessionContext() {

	}
}
