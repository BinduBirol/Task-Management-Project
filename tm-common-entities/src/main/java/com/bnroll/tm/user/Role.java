package com.bnroll.tm.user;

public enum Role {

	ROLE_USER("User"), ROLE_ADMIN("Administrator"), ROLE_MANAGER("Project Manager");

	private final String displayName;

	Role(String displayName) {
		this.displayName = displayName;
	}

	public String getDisplayName() {
		return displayName;
	}

	@Override
	public String toString() {
		return displayName;
	}
}