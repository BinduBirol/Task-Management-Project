package com.bnroll.tm.auth.service;

import com.bnroll.tm.user.Provider;
import com.bnroll.tm.user.Role;
import com.bnroll.tm.user.User;
import com.bnroll.tm.auth.util.JwtUtil;
import com.bnroll.tm.auth.repo.UserRepository;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class GoogleLoginService {

	private static final String CLIENT_ID = "937469130987-olf8n3ebkjuh2fc9h190frplfi386m4u.apps.googleusercontent.com";

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private JwtUtil jwtUtil;

	public String loginWithGoogle(String idTokenString) {
		if (idTokenString == null || idTokenString.isEmpty()) {
			throw new IllegalArgumentException("Google ID token is missing");
		}

		GoogleIdToken idToken = null;
		try {
			var verifier = new GoogleIdTokenVerifier.Builder(GoogleNetHttpTransport.newTrustedTransport(),
					GsonFactory.getDefaultInstance()).setAudience(Collections.singletonList(CLIENT_ID)).build();

			idToken = verifier.verify(idTokenString);
		} catch (Exception e) {
			e.printStackTrace();			
			throw new RuntimeException("Failed to verify Google ID token", e);
		}

		if (idToken == null) {
			throw new IllegalArgumentException("Invalid Google ID token");
		}

		GoogleIdToken.Payload payload = idToken.getPayload();

		// Safe extraction of optional fields
		String email = payload.getEmail();
		if (email == null || email.isEmpty()) {
			throw new IllegalArgumentException("Google ID token does not contain an email");
		}

		String name = payload.get("name") != null ? payload.get("name").toString() : "Unknown";
		String picture = payload.get("picture") != null ? payload.get("picture").toString() : null;
		String googleId = payload.getSubject() != null ? payload.getSubject() : null;

		// Create user if not exists
		User user = userRepository.findByEmail(email).orElseGet(() -> {
			User u = new User();
			u.setName(name);
			u.setImageUrl(picture);
			u.setRole(Role.ROLE_USER);
			u.setEmail(email);
			u.setProvider(Provider.GOOGLE);
			return userRepository.save(u);
		});

		// Generate your own JWT
		return jwtUtil.generateToken(email);
	}
}