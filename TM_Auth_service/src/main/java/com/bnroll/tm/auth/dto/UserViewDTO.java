package com.bnroll.tm.auth.dto;

import java.time.LocalDateTime;

public class UserViewDTO {

	private String name;
	private String email;
	private String userId;
	private String role;
	private String provider;
	private String imageUrl;
	private LocalDateTime createdAt;

	public UserViewDTO() {
	}

	public UserViewDTO(String name, String email, String userId, String role, String provider, String imageUrl,
			LocalDateTime createdAt) {
		this.name = name;
		this.email = email;
		this.userId = userId;
		this.role = role;
		this.provider = provider;
		this.imageUrl = imageUrl;
		this.createdAt = createdAt;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getProvider() {
		return provider;
	}

	public void setProvider(String provider) {
		this.provider = provider;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

}
