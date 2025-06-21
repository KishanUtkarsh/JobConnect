package com.jobconnect.user.service;

import com.jobconnect.user.dto.JobSeekerRequestDTO;
import com.jobconnect.user.dto.JobSeekerResponseDTO;
import org.springframework.security.core.Authentication;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

public interface JobSeekerService {

    JobSeekerResponseDTO getJobSeeker(UUID userId);

    JobSeekerResponseDTO updateJobSeeker(JobSeekerRequestDTO jobSeekerRequestDTO, Authentication authentication);

    String deleteJobSeeker(UUID userId);

    String uploadResume(MultipartFile file, Authentication authentication);

    String deleteResume(Authentication authentication);

}
