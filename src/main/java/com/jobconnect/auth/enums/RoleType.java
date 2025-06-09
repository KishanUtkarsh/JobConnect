package com.jobconnect.auth.enums;

public enum RoleType {
    ROLE_ADMIN("ADMIN"),
    ROLE_JOBSEEKER("JOBSEEKER"),
    ROLE_RECRUITER("RECRUITER");

    private final String role;

    RoleType(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }
}
