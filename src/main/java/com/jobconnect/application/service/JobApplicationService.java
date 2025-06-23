package com.jobconnect.application.service;

import com.jobconnect.application.dto.JobApplicationRequestDTO;
import com.jobconnect.application.dto.JobApplicationResponseDTO;
import com.jobconnect.application.dto.JobApplicationUpdateDTO;
import com.jobconnect.application.dto.JobApplyResponseDTO;
import org.springframework.security.core.Authentication;

import java.util.List;
import java.util.UUID;

public interface JobApplicationService {

    // Methods to get/delete/apply for a job (JobSeeker)
    JobApplyResponseDTO applyForJob(JobApplicationRequestDTO jobApplication, Authentication authentication);
    JobApplicationResponseDTO getJobApplicationById(UUID applicationId, Authentication authentication);
    String deleteJobApplication(UUID applicationId, Authentication authentication);
    List<JobApplyResponseDTO> getJobApplicationByJobSeekerId(Authentication authentication);

    // Methods to get/update job applications (Employer)
    List<JobApplyResponseDTO> getJobApplicationByJobId(UUID jobId, Authentication authentication);
    String updateJobApplicationStatus(JobApplicationUpdateDTO dto, Authentication authentication);

}
