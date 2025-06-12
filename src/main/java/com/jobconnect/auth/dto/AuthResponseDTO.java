package com.jobconnect.auth.dto;

public record AuthResponseDTO(
        String jwtToken,
        String tokenType,
        long expiresIn
) {
}
