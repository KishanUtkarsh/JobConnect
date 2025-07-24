package com.jobconnect.user.service;

import com.jobconnect.user.dto.JobSeekerRequestDTO;
import com.jobconnect.user.dto.JobSeekerResponseDTO;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.security.core.Authentication;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.util.UUID;

public interface JobSeekerService {

    JobSeekerResponseDTO getJobSeeker(UUID userId);

    JobSeekerResponseDTO updateJobSeeker(JobSeekerRequestDTO jobSeekerRequestDTO, Authentication authentication);

    String deleteJobSeeker(UUID userId);

    Mono<String> uploadResume(FilePart file, Authentication authentication) throws IOException;

    String deleteResume(Authentication authentication);

}
