package com.jobconnect.application.dto;

import java.util.UUID;

public record JobApplicationUpdateDTO(
        UUID applicationId,
        String status
) {
}
