package com.jobconnect.user.service;

import com.jobconnect.user.dto.JobSeekerRequestDTO;
import com.jobconnect.user.dto.JobSeekerResponseDTO;

import java.util.UUID;

public interface JobSeekerService {

    JobSeekerResponseDTO getJobSeeker(UUID userId);

    JobSeekerResponseDTO updateJobSeeker(UUID userId, JobSeekerRequestDTO jobSeekerRequestDTO);

    String deleteJobSeeker(UUID userId);

}
