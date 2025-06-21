package com.jobconnect.job.service.impl;

import com.jobconnect.common.exception.PermissionDeniedException;
import com.jobconnect.common.exception.ResourceNotFoundException;
import com.jobconnect.common.util.JobMapperUtil;
import com.jobconnect.job.dto.JobDto;
import com.jobconnect.job.entity.Job;
import com.jobconnect.job.enums.JobStatus;
import com.jobconnect.job.service.JobService;
import com.jobconnect.job.spec.JobSpecification;
import com.jobconnect.repository.JobRepository;
import com.jobconnect.repository.RecruiterRepository;
import com.jobconnect.user.entity.Recruiter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Slf4j
@Service
public class JobServiceImpl implements JobService {

    private final JobRepository jobRepository;
    private final RecruiterRepository recruiterRepository;
    public JobServiceImpl(JobRepository jobRepository, RecruiterRepository recruiterRepository) {
        this.jobRepository = jobRepository;
        this.recruiterRepository = recruiterRepository;
    }


    @Override
    public List<JobDto> findAllJobs(Set<String> skills, String location, String jobType, String keyword, int page, int size) {

        Pageable pageable = PageRequest.of(page, size , Sort.by("createdAt").descending());

        Specification<Job> specs = Specification.allOf(
                skills != null && !skills.isEmpty() ? JobSpecification.hasSkills(skills) : null,
                location != null ? JobSpecification.hasLocation(location) : null,
                jobType != null ? JobSpecification.hasJobType(jobType) : null,
                keyword != null ? JobSpecification.hasKeyword(keyword) : null
        );

        List<Job> jobs = jobRepository.findAll(specs, pageable).getContent();

        log.info("Found {} jobs matching the criteria", jobs.size());

        return jobs.stream()
                .filter(job -> job.getStatus() != JobStatus.DELETED) // Exclude deleted jobs
                .map(JobMapperUtil::jobEntityToJobDto)
                .toList();
    }

    @Override
    public JobDto createJob(JobDto jobDto, Authentication authentication) {
        UUID userId = (UUID) authentication.getPrincipal();
        Recruiter recruiter = recruiterRepository.findRecruiterByUserId(userId);

        Job job = jobRepository.save(JobMapperUtil.jobDtoToJobEntity(jobDto, recruiter));
        log.info("Job created with ID: {}", job.getId());
        return JobMapperUtil.jobEntityToJobDto(job);
    }

    @Override
    public JobDto updateJob(JobDto jobDto, Authentication authentication) {
        Job existingJob = jobRepository.findById(jobDto.id())
                .orElseThrow(() -> new ResourceNotFoundException("Job not found with id: " + jobDto.id()));

        if(!existingJob.getRecruiter().getUser().equals(authentication.getPrincipal())) {
            throw new PermissionDeniedException("You do not have permission to update this job.");
        }

        Job updatedJob = JobMapperUtil.updateJobEntityFromDto(existingJob, jobDto);
        updatedJob = jobRepository.save(updatedJob);

        log.info("Job with ID {} has been updated", updatedJob.getId());
        return JobMapperUtil.jobEntityToJobDto(updatedJob);

    }

    @Override
    public String deleteJob(UUID jobId, Authentication authentication) {
        Job job = jobRepository.findById(jobId)
                .orElseThrow(() -> new ResourceNotFoundException("Job not found with id: " + jobId));

        if(!job.getRecruiter().getUser().equals(authentication.getPrincipal())) {
            throw new PermissionDeniedException("You do not have permission to delete this job.");
        }

        job.setStatus(JobStatus.valueOf("DELETED"));
        jobRepository.save(job);

        log.info("Job with ID {} has been marked as deleted", jobId);
        return "Job with ID " + jobId + " has been deleted successfully.";
    }
}
