package com.bnroll.tm.user;

import jakarta.persistence.*;
import java.util.concurrent.ThreadLocalRandom;

import com.bnroll.tm.common.BaseEntity;

@Entity
@Table(name = "users", uniqueConstraints = { @UniqueConstraint(columnNames = "email"),
		@UniqueConstraint(columnNames = "userId") })
public class User extends BaseEntity {

	@Column(nullable = false)
	private String name;

	@Column(nullable = false)
	private String password;

	@Column(nullable = false, unique = true)
	private String email;

	@Column(nullable = false, unique = true, updatable = true)
	private String userId; // unique string ID

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private Provider provider; // GOOGLE, LOCAL, etc.

	private String imageUrl; // optional profile picture

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private Role role;

	// Generate a random UUID for userId when creating new users
	@PrePersist
	private void prePersist() {
		if (userId == null) {
			userId = generateDefaultUserId();
		}
	}

	private String generateDefaultUserId() {
		String[] adjectives = { "cold", "hot", "gray", "blue", "happy", "silent", "bright" };
		String[] nouns = { "bird", "cat", "tiger", "sky", "moon", "star" };
		int number = ThreadLocalRandom.current().nextInt(100, 999); // 3-digit number

		String adj = adjectives[ThreadLocalRandom.current().nextInt(adjectives.length)];
		String noun = nouns[ThreadLocalRandom.current().nextInt(nouns.length)];

		return adj + "-" + noun + "-" + number; // e.g., gray-bird-123
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUserId() {
		return userId;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public Provider getProvider() {
		return provider;
	}

	public void setProvider(Provider provider) {
		this.provider = provider;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

}