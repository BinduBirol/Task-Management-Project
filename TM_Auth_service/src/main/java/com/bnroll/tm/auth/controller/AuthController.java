package com.bnroll.tm.auth.controller;

import java.util.HashMap;
import java.util.Map;

import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticatedPrincipal;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.bnroll.tm.auth.dto.GoogleLoginRequest;
import com.bnroll.tm.auth.dto.LoginRequest;
import com.bnroll.tm.auth.dto.UserRegistrationRequest;
import com.bnroll.tm.auth.dto.UserViewDTO;
import com.bnroll.tm.auth.service.GoogleLoginService;
import com.bnroll.tm.auth.service.LoginService;
import com.bnroll.tm.auth.service.RegistrationService;
import com.bnroll.tm.user.User;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@Tag(name = "Authentication", description = "Endpoints for user authentication")
public class AuthController {

	@Autowired
	private RegistrationService registrationService;

	@Autowired
	private LoginService loginService;

	@Autowired
	private GoogleLoginService googleLoginService;

	@Operation(summary = "Register a new user")
	@PostMapping("/register")
	public ResponseEntity<?> registerUser(@Valid @RequestBody UserRegistrationRequest request) {
		User savedUser = registrationService.registerUser(request);

		Map<String, Object> response = new HashMap<>();
		response.put("message", "User registered successfully");
		response.put("userId", savedUser.getUserId());
		response.put("id", savedUser.getId());

		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}

	@Operation(summary = "Login user and get JWT token")
	@PostMapping("/login")
	public ResponseEntity<?> loginUser(@Valid @RequestBody LoginRequest request) {

		String token = loginService.loginUser(request);

		return ResponseEntity.ok(Map.of("message", "Login successful", "token", "Bearer " + token));
	}

	@PostMapping("/login/google")
	public ResponseEntity<?> googleLogin(@RequestBody GoogleLoginRequest request) throws Exception {

		String token = googleLoginService.loginWithGoogle(request.getCredential());

		return ResponseEntity.ok(Map.of("message", "Login successful via Google", "token", "Bearer " + token));
	}

	@Operation(summary = "Gets logged in user info")
	@GetMapping("/user/this")
	public UserViewDTO getLoggedInUser(Authentication auth) {
		String name = auth.getName();
		UserViewDTO user = loginService.getLoggedInUser(name);
		return user;
	}

}