package com.jobconnect.user.dto;

import com.jobconnect.job.enums.EmploymentType;
import java.util.Set;
import java.util.UUID;

public record JobSeekerResponseDTO(
        UUID uuid,
        UUID userId,
        String resumeUrl,
        Set<String> skills,
        String degree,
        EmploymentType employmentType
) {
}
