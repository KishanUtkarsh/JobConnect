package com.jobconnect.auth.dto;

public record LoginResponse(
        String email,
        String otp
) {
}
