package com.jobconnect.auth.dto;

public record UserResponse(
    Long id,
    String firstName,
    String lastName,
    String email,
    String role,
    String status
) {
}
