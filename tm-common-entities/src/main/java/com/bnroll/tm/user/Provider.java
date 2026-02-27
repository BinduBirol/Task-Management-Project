package com.bnroll.tm.user;

public enum Provider {
    LOCAL("Local"),
    GOOGLE("Google");

    private final String displayName;

    Provider(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}