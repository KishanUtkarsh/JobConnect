package com.jobconnect.auth.dto;

public record AuthResponse (
        String jwtToken,
        String tokenType,
        long expiresIn
) {
}
