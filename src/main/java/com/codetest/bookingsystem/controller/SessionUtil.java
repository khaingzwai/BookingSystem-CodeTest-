package com.codetest.bookingsystem.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.codetest.bookingsystem.model.User;

public class SessionUtil {
	public static SessionContext getCurrentSession() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		User userDetails = (User) auth.getPrincipal();

		SessionContext session = new SessionContext();
		session.setUserId(userDetails.getId());
		session.setUserName(userDetails.getUserName());
		session.setRole(userDetails.getRole());

		return session;
	}
}
