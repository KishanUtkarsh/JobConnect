package com.jobconnect.job.service;

import com.jobconnect.job.dto.JobDto;

import java.util.List;
import java.util.UUID;

public interface JobService {

    List<JobDto> findAllJobs(
            List<String> skills,
            String location,
            String jobType,
            String keyword,
            int page,
            int size
    );

    JobDto createJob(JobDto jobDto);
    JobDto updateJob(JobDto jobDto);
    String deleteJob(UUID jobId);

}
