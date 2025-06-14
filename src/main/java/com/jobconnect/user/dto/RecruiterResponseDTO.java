package com.jobconnect.user.dto;

import java.util.UUID;

public record RecruiterResponseDTO(
        UUID uuid,
        UUID userId,
        String company,
        String position,
        String companyUrl
) {
}
