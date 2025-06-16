package com.jobconnect.application.service;

import com.jobconnect.application.dto.JobApplicationRequestDTO;
import com.jobconnect.application.dto.JobApplicationResponseDTO;
import com.jobconnect.application.dto.JobApplyResponseDTO;

import java.util.List;
import java.util.UUID;

public interface JobApplicationService {

    // Methods to get/delete/apply for a job (JobSeeker)
    JobApplyResponseDTO applyForJob(JobApplicationRequestDTO jobApplication, UUID userId);
    JobApplicationResponseDTO getJobApplicationById(UUID applicationId, UUID userId);
    String deleteJobApplication(UUID applicationId, UUID userId);

    // Methods to get/update job applications (Employer)
    List<JobApplyResponseDTO> getJobApplicationByJobId(UUID jobId, UUID userId);
    List<JobApplyResponseDTO> getJobApplicationByJobSeekerId(UUID userId);
    String updateJobApplicationStatus(UUID applicationId, String status, UUID userId);

}
