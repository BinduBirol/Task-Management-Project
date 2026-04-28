package com.bnroll.tm.auth.service;

import java.time.Duration;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.bnroll.tm.auth.config.redis.RedisJsonService;
import com.bnroll.tm.auth.dto.LoginRequest;
import com.bnroll.tm.auth.dto.UserViewDTO;
import com.bnroll.tm.auth.util.JwtUtil;
import com.bnroll.tm.user.Provider;
import com.bnroll.tm.user.User;
import com.bnroll.tm.auth.repo.UserRepository;

@Service
public class LoginService {

	private final AuthenticationManager authenticationManager;
	private final UserRepository userRepository;
	private final JwtUtil jwtUtil;
	private final RedisJsonService redisJsonService;

	public LoginService(AuthenticationManager authenticationManager, UserRepository userRepository, JwtUtil jwtUtil,
			RedisJsonService redisJsonService) {
		this.authenticationManager = authenticationManager;
		this.userRepository = userRepository;
		this.jwtUtil = jwtUtil;
		this.redisJsonService = redisJsonService;
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

	public UserViewDTO getLoggedInUser(String email) {
		String key = "userProfile:" + email;

		UserViewDTO cached = redisJsonService.get(key, UserViewDTO.class);

		if (cached != null) {
			return cached;
		}

		UserViewDTO dto = userRepository.findByEmail(email)
				.map(user -> new UserViewDTO(user.getName(), user.getEmail(), user.getUserId(), user.getRole().getDisplayName(),
						user.getProvider().name(), user.getImageUrl(), user.getCreatedAt()))
				.orElseThrow(() -> new RuntimeException("User not found"));
		redisJsonService.set(key, dto, Duration.ofMinutes(30));

		return dto;
	}
}