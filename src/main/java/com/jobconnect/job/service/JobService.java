package com.jobconnect.job.service;

import com.jobconnect.job.dto.JobRequestDto;
import com.jobconnect.job.dto.JobResponseDto;
import org.springframework.security.core.Authentication;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface JobService {

    List<JobResponseDto> findAllJobs(
            Set<String> skills,
            String location,
            String jobType,
            String keyword,
            int page,
            int size
    );

    JobResponseDto createJob(JobRequestDto jobDto , Authentication authentication);
    JobResponseDto updateJob(JobRequestDto jobDto, Authentication authentication);
    String deleteJob(UUID jobId, Authentication authentication);

}
