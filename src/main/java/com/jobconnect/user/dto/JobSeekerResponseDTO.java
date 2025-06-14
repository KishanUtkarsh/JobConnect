package com.jobconnect.user.dto;

import com.jobconnect.job.enums.EmploymentType;

import java.util.List;
import java.util.UUID;

public record JobSeekerResponseDTO(
        UUID uuid,
        UUID userId,
        String resumeUrl,
        List<String> skills,
        String degree,
        EmploymentType employmentType

) {
}
