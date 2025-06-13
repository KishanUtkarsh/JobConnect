package com.jobconnect.job.dto;

import com.jobconnect.job.enums.EmploymentType;
import com.jobconnect.job.enums.JobStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public record JobDto(
        // OUTPUT FIELDS
        Integer id,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,

        // INPUT + OUTPUT FIELDS
        @NotBlank String title,
        @NotBlank String role,
        @NotNull List<String> skillSet,
        @NotBlank String description,
        @NotBlank String location,
        @NotBlank String companyName,
        @NotNull EmploymentType employmentType,
        @NotBlank String salaryRange,
        @NotBlank String requirements,
        String benefits,
        @NotNull LocalDate applicationDeadline,
        @NotNull JobStatus status
) {
}
