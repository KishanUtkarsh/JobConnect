package com.jobconnect.application.service.impl;

import com.jobconnect.application.dto.JobApplicationRequestDTO;
import com.jobconnect.application.dto.JobApplicationResponseDTO;
import com.jobconnect.application.dto.JobApplyResponseDTO;
import com.jobconnect.application.entity.JobApplication;
import com.jobconnect.application.enums.ApplicationStatus;
import com.jobconnect.application.service.JobApplicationService;
import com.jobconnect.auth.entity.User;
import com.jobconnect.common.exception.ConflictException;
import com.jobconnect.common.util.JobApplicationMapperUtil;
import com.jobconnect.job.entity.Job;
import com.jobconnect.repository.*;
import com.jobconnect.user.entity.JobSeeker;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class JobApplicationServiceImpl implements JobApplicationService {

    private final JobApplicationRepository jobApplicationRepository;
    private final JobRepository jobRepository;
    private final UserRepository userRepository;
    private final JobSeekerRepository jobSeekerRepository;

    public JobApplicationServiceImpl(JobApplicationRepository jobApplicationRepository,
                                     JobRepository jobRepository,
                                     UserRepository userRepository, JobSeekerRepository jobSeekerRepository) {
        this.jobApplicationRepository = jobApplicationRepository;
        this.jobRepository = jobRepository;
        this.userRepository = userRepository;
        this.jobSeekerRepository = jobSeekerRepository;
    }

    @Override
    @Transactional
    public JobApplyResponseDTO applyForJob(JobApplicationRequestDTO jobApplication, UUID userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + userId));

        if(user.getRole().toString().equals("JOB_SEEKER") ) {
            throw new IllegalArgumentException("User is not a Job Seeker");
        }

        Job job = jobRepository.findById(jobApplication.jobId())
                .orElseThrow(() -> new IllegalArgumentException("Job not found with id: " + jobApplication.jobId()));

        JobSeeker jobSeeker = jobSeekerRepository.findJobSeekerByUserId(user.getId());

        JobApplication jobApplicationCheck = jobApplicationRepository.findJobApplicationByJobIdAndJobSeekerId(jobApplication.jobId(), jobSeeker.getId());

         if (jobApplicationCheck != null) {
            throw new ConflictException("You have already applied for this job");
        }


        JobApplication application = JobApplicationMapperUtil.convertToJobApplicationEntity(
                jobSeeker.getResumeUrl(),
                job,
                jobSeeker,
                ApplicationStatus.PENDING
        );
        JobApplication savedApplication = jobApplicationRepository.save(application);
        return JobApplicationMapperUtil.convertToJobApplyResponseDTO(savedApplication, job);
    }

    @Override
    @Transactional
    public JobApplicationResponseDTO getJobApplicationById(UUID applicationId, UUID userId) {
        JobApplication jobApplication = jobApplicationRepository.findById(applicationId)
                .orElseThrow(() -> new IllegalArgumentException("Job application not found with id: " + applicationId));
        if(!jobApplication.getJobSeeker().getUser().getId().equals(userId)) {
            throw new IllegalArgumentException("User is not authorized to view this job application");
        }
        return JobApplicationMapperUtil.convertToJobApplicationResponseDTO(
                jobApplication,
                jobRepository.findById(jobApplication.getJob().getId())
                        .orElseThrow(() -> new IllegalArgumentException("Job not found with id: " + jobApplication.getJob().getId())),
                jobSeekerRepository.findJobSeekerByUserId(userId)
        );
    }

    @Override
    @Transactional
    public String deleteJobApplication(UUID applicationId, UUID userId) {
        JobApplication jobApplication = jobApplicationRepository.findById(applicationId)
                .orElseThrow(() -> new IllegalArgumentException("Job application not found with id: " + applicationId));
        if (!jobApplication.getJobSeeker().getUser().getId().equals(userId)) {
            throw new IllegalArgumentException("User is not authorized to delete this job application");
        }
        jobApplicationRepository.delete(jobApplication);
        return "Job application deleted successfully";
    }

    @Override
    public List<JobApplyResponseDTO> getJobApplicationByJobId(UUID jobId, UUID userId) {
        Job job = jobRepository.findById(jobId)
                .orElseThrow(() -> new IllegalArgumentException("Job not found with id: " + jobId));
        if (!job.getRecruiter().getUser().getId().equals(userId)) {
            throw new IllegalArgumentException("User is not authorized to view applications for this job");
        }

        List<JobApplication> applications = jobApplicationRepository.findJobApplicationByJobId(jobId);
        return applications.stream()
                .map(application -> JobApplicationMapperUtil.convertToJobApplyResponseDTO(application, job))
                .toList();
    }

    @Override
    public List<JobApplyResponseDTO> getJobApplicationByJobSeekerId(UUID userId) {
    JobSeeker jobSeeker = jobSeekerRepository.findJobSeekerByUserId(userId);
        if (jobSeeker == null) {
            throw new IllegalArgumentException("Job Seeker not found with user id: " + userId);
        }
        List<JobApplication> applications = jobApplicationRepository.findJobApplicationByJobSeekerId(jobSeeker.getId());
        return applications.stream()
                .map(application -> JobApplicationMapperUtil.convertToJobApplyResponseDTO(application, application.getJob()))
                .toList();
    }

    @Override
    @Transactional
    public String updateJobApplicationStatus(UUID applicationId, String status, UUID userId) {
    JobApplication jobApplication = jobApplicationRepository.findById(applicationId)
            .orElseThrow(() -> new IllegalArgumentException("Job application not found with id: " + applicationId));
    if (!jobApplication.getJob().getRecruiter().getUser().getId().equals(userId)) {
        throw new IllegalArgumentException("User is not authorized to update this job application status");
    }
        try {
            ApplicationStatus applicationStatus = ApplicationStatus.valueOf(status.toUpperCase());
            jobApplication.setStatus(applicationStatus);
            jobApplicationRepository.save(jobApplication);
            return "Job application status updated successfully";
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid status value: " + status);
    }
    }
}
