package com.bnroll.tm.auth.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.bnroll.tm.auth.dto.UserRegistrationRequest;
import com.bnroll.tm.auth.repo.UserRepository;
import com.bnroll.tm.user.Provider;
import com.bnroll.tm.user.Role;
import com.bnroll.tm.user.User;

@Service
public class RegistrationService {

	private final UserRepository userRepository;
	private final BCryptPasswordEncoder passwordEncoder;

	public RegistrationService(UserRepository userRepository) {
		this.userRepository = userRepository;
		this.passwordEncoder = new BCryptPasswordEncoder();
	}

	public User registerUser(UserRegistrationRequest request) {

	    if (userRepository.existsByEmail(request.getEmail())) {
	        throw new IllegalArgumentException("User already registered");
	    }

	    User user = new User();
	    user.setName(request.getName());
	    user.setEmail(request.getEmail());
	    user.setProvider(Provider.LOCAL);
	    user.setPassword(passwordEncoder.encode(request.getPassword()));
	    user.setRole(Role.ROLE_USER);

	    // ⭐ generate readable unique userId
	    //user.setUserId(generateUniqueUserId(request.getName()));

	    return userRepository.save(user);
	}
}