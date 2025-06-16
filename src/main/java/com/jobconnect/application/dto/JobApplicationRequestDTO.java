package com.jobconnect.application.dto;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record JobApplicationRequestDTO(
        @NotNull UUID jobId
) {
}
