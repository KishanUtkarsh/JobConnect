package com.jobconnect.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record OtpRequest(
        @Email @NotBlank String email,
        @NotBlank String otp
) {
}
