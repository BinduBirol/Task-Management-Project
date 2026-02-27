package com.bnroll.tm.auth.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.bnroll.tm.auth.dto.LoginRequest;
import com.bnroll.tm.auth.util.JwtUtil;
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

		Authentication authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

		// authentication successful at this point

		User user = userRepository.findByEmail(request.getEmail())
				.orElseThrow(() -> new UsernameNotFoundException("User not found"));

		return jwtUtil.generateToken(user.getEmail());
	}
}