package com.jobconnect.application.dto;

public record JobApplicationResponseDTO(
    String id,
    String jobId,
    String jobTitle,
    String jobDescription,
    String jobLocation,
    String companyName,
    String jobSeekerId,
    String status,
    String appliedAt,
    String resumeLink,
    String degree,
    String employmentType
) {
}
