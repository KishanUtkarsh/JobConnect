package com.jobconnect.job.service.impl;

import com.jobconnect.common.exception.PermissionDeniedException;
import com.jobconnect.common.exception.ResourceNotFoundException;
import com.jobconnect.common.util.JobMapperUtil;
import com.jobconnect.job.dto.JobRequestDto;
import com.jobconnect.job.dto.JobResponseDto;
import com.jobconnect.job.entity.Job;
import com.jobconnect.job.enums.JobStatus;
import com.jobconnect.job.service.JobService;
import com.jobconnect.job.spec.JobSpecification;
import com.jobconnect.repository.JobRepository;
import com.jobconnect.repository.RecruiterRepository;
import com.jobconnect.user.entity.Recruiter;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
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
    @Cacheable(
            value = "JOBS_CACHE",
            key = "{#skills, #location, #jobType, #keyword, #page, #size}"
    )
    public List<JobResponseDto> findAllJobs(Set<String> skills, String location, String jobType, String keyword, int page, int size) {

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
    @Cacheable(value = "JOB_CACHE", key = "#jobId")
    public JobResponseDto getJobById(UUID jobId) {
        Job job = jobRepository.findById(jobId)
                .orElseThrow(() -> new ResourceNotFoundException("Job not found with id: " + jobId));
        if (job.getStatus() == JobStatus.DELETED) {
            log.warn("Job with ID {} is marked as deleted", jobId);
            throw new PermissionDeniedException("Job with ID " + jobId + " is marked as deleted.");
        }
        return JobMapperUtil.jobEntityToJobDto(job);
    }

    @Override
    @CachePut(value = "JOB_CACHE", key = "#result.id()")
    @Transactional
    public JobResponseDto createJob(JobRequestDto jobDto, Authentication authentication) {
        UUID userId = (UUID) authentication.getPrincipal();
        Recruiter recruiter = recruiterRepository.findRecruiterByUserId(userId);

        Job job = jobRepository.save(JobMapperUtil.jobDtoToJobEntity(jobDto, recruiter));
        log.info("Job created with ID: {}", job.getId());
        return JobMapperUtil.jobEntityToJobDto(job);
    }

    @Override
    @CachePut(value = "JOB_CACHE", key = "#result.id()")
    @Transactional
    public JobResponseDto updateJob(JobRequestDto jobDto, Authentication authentication) {
        Job existingJob = jobRepository.findById(jobDto.id())
                .orElseThrow(() -> new ResourceNotFoundException("Job not found with id: " + jobDto.id()));

        if(!existingJob.getRecruiter().getUser().getId().equals(authentication.getPrincipal())) {
            throw new PermissionDeniedException("You do not have permission to update this job.");
        }

        Job updatedJob = JobMapperUtil.updateJobEntityFromDto(existingJob, jobDto);
        updatedJob = jobRepository.save(updatedJob);

        log.info("Job with ID {} has been updated", updatedJob.getId());
        return JobMapperUtil.jobEntityToJobDto(updatedJob);

    }

    @Override
    @CacheEvict(value = "JOB_CACHE", key = "#jobId")
    @Transactional
    public String deleteJob(UUID jobId, Authentication authentication) {
        Job job = jobRepository.findById(jobId)
                .orElseThrow(() -> new ResourceNotFoundException("Job not found with id: " + jobId));

        if(!job.getRecruiter().getUser().getId().equals(authentication.getPrincipal())) {
            throw new PermissionDeniedException("You do not have permission to delete this job.");
        }

        if(job.getStatus() == JobStatus.DELETED) {
            log.warn("Job with ID {} is already marked as deleted", jobId);
            throw new PermissionDeniedException("Job with ID " + jobId + " is already marked as deleted.");
        }

        job.setStatus(JobStatus.valueOf("DELETED"));
        jobRepository.save(job);

        log.info("Job with ID {} has been marked as deleted", jobId);
        return "Job with ID " + jobId + " has been deleted successfully.";
    }
}
