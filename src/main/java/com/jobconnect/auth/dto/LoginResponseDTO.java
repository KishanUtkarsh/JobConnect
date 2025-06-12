package com.jobconnect.auth.dto;

public record LoginResponseDTO(
        String email,
        String otp
) {
}
