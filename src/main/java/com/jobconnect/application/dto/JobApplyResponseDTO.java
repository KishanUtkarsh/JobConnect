package com.jobconnect.application.dto;

import com.jobconnect.application.enums.ApplicationStatus;

import java.time.LocalDate;
import java.util.UUID;

public record JobApplyResponseDTO(
        UUID applicationId,
    UUID jobId,
    String jobTitle,
    ApplicationStatus status,
    LocalDate appliedAt
) {
}
