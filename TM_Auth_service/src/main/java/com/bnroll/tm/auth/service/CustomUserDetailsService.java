package com.bnroll.tm.auth.service;

import java.util.Collections;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.bnroll.tm.auth.repo.UserRepository;
import com.bnroll.tm.user.User;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	private final UserRepository userRepository;

	public CustomUserDetailsService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

		User user = userRepository.findByEmail(email)
				.orElseThrow(() -> new UsernameNotFoundException("User not found"));

		String password = user.getPassword();

		if (password == null) {
			password = "{noop}GOOGLE_USER"; // dummy password for OAuth users
		}

		return org.springframework.security.core.userdetails.User.builder().username(user.getEmail()).password(password)
				.authorities(Collections.singleton(new SimpleGrantedAuthority(user.getRole().name()))).build();
	}
}