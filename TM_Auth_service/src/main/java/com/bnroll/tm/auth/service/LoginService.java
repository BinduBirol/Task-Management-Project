package com.bnroll.tm.auth.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.bnroll.tm.auth.dto.LoginRequest;
import com.bnroll.tm.auth.util.JwtUtil;
import com.bnroll.tm.user.Provider;
import com.bnroll.tm.user.User;
import com.bnroll.tm.auth.repo.UserRepository;

@Service
public class LoginService {

	private final AuthenticationManager authenticationManager;
	private final UserRepository userRepository;
	private final JwtUtil jwtUtil;

	public LoginService(AuthenticationManager authenticationManager, UserRepository userRepository, JwtUtil jwtUtil) {
		this.authenticationManager = authenticationManager;
		this.userRepository = userRepository;
		this.jwtUtil = jwtUtil;
	}

	public String loginUser(LoginRequest request) {

		User user = userRepository.findByEmail(request.getEmail())
				.orElseThrow(() -> new UsernameNotFoundException("Invalid email or password"));

		// Block non-local users from password login
		if (user.getProvider() != Provider.LOCAL) {
			throw new IllegalStateException("This account uses Google login. Please sign in with Google.");
		}

		Authentication authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

		return jwtUtil.generateToken(user.getEmail());
	}

	public User getLoggedInUser(String email) {

		return userRepository.findByEmail(email).map(user -> {
			user.setPassword(null);
			return user;
		}).orElseThrow(() -> new RuntimeException("User not found"));
	}
}