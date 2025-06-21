package com.jobconnect.job.service;

import com.jobconnect.job.dto.JobDto;
import org.springframework.security.core.Authentication;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface JobService {

    List<JobDto> findAllJobs(
            Set<String> skills,
            String location,
            String jobType,
            String keyword,
            int page,
            int size
    );

    JobDto createJob(JobDto jobDto , Authentication authentication);
    JobDto updateJob(JobDto jobDto, Authentication authentication);
    String deleteJob(UUID jobId, Authentication authentication);

}
