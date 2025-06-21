package com.jobconnect.user.dto;

import com.jobconnect.job.enums.EmploymentType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Set;

public record JobSeekerRequestDTO(
        Set<String> skills,
        @NotBlank String degree,
        @NotNull EmploymentType employmentType
) {
}
