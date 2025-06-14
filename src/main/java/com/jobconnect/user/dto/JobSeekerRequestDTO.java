package com.jobconnect.user.dto;

import com.jobconnect.job.enums.EmploymentType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record JobSeekerRequestDTO(
        List<String> skills,
        @NotBlank String degree,
        @NotNull EmploymentType employmentType
) {
}
