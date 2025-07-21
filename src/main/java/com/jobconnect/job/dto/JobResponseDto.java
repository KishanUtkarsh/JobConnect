package com.jobconnect.job.dto;

import com.jobconnect.job.enums.EmploymentType;
import com.jobconnect.job.enums.JobStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Set;
import java.util.UUID;

public record JobResponseDto(
        UUID id,
        String createdAt,
        String updatedAt,
        RecruiterDTO recruiter,
        @NotBlank String title,
        @NotBlank String role,
        @NotNull Set<String> skills,
        @NotBlank String description,
        @NotBlank String location,
        @NotBlank String companyName,
        @NotNull EmploymentType employmentType,
        @NotBlank String salaryRange,
        @NotBlank String degree,
        String benefits,
        @NotNull String applicationDeadline,
        @NotNull JobStatus status
) {
}
