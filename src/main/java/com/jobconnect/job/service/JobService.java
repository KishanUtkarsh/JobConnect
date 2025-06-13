package com.jobconnect.job.service;

import com.jobconnect.job.dto.JobDto;

import java.util.List;

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
    String deleteJob(Long jobId);

}
