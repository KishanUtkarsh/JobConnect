package com.jobconnect.auth.dto;

public record UserResponseDTO(
    Long id,
    String firstName,
    String lastName,
    String email,
    String role,
    String status
) {
}
