package com.bnroll.tm.auth.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.bnroll.tm.auth.dto.LoginRequest;
import com.bnroll.tm.auth.dto.UserRegistrationRequest;
import com.bnroll.tm.auth.service.LoginService;
import com.bnroll.tm.auth.service.RegistrationService;
import com.bnroll.tm.user.User;

import jakarta.validation.Valid;

@RestController
public class AuthController {

	@Autowired
	private RegistrationService registrationService;

	@Autowired
	private LoginService loginService;

	@PostMapping("/register")
	public ResponseEntity<?> registerUser(@Valid @RequestBody UserRegistrationRequest request) {
		User savedUser = registrationService.registerUser(request);

		Map<String, Object> response = new HashMap<>();
		response.put("message", "User registered successfully");
		response.put("userId", savedUser.getUserId());
		response.put("id", savedUser.getId());

		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}

	@PostMapping("/login")
	public ResponseEntity<?> loginUser(@Valid @RequestBody LoginRequest request) {

		String token = loginService.loginUser(request);

		return ResponseEntity.ok(Map.of("message", "Login successful", "token", "Bearer " + token));
	}
}