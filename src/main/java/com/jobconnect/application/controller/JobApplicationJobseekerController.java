package com.jobconnect.application.controller;

import com.jobconnect.application.dto.JobApplicationRequestDTO;
import com.jobconnect.application.dto.JobApplicationResponseDTO;
import com.jobconnect.application.dto.JobApplyResponseDTO;
import com.jobconnect.application.service.impl.JobApplicationServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RequestMapping("/api/v1/jobApplication-jobseeker")
@RestController
@Tag(name = "JobApplication JobSeeker Controller Api's", description = "JobApplication JobSeeker related operations")
public class JobApplicationJobseekerController {

    private final JobApplicationServiceImpl jobApplicationService;
    public JobApplicationJobseekerController(JobApplicationServiceImpl jobApplicationService) {
        this.jobApplicationService = jobApplicationService;
    }

    @PostMapping("/apply-job")
    @Operation(summary = "Apply for a job")
    @PreAuthorize("hasRole('JOBSEEKER')")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<JobApplyResponseDTO> applyJob(@RequestBody JobApplicationRequestDTO jobApplicationRequestDTO,
                                              Authentication authentication) {
        return Mono.just(jobApplicationService.applyForJob(jobApplicationRequestDTO,authentication));
    }

    @GetMapping("/get-application/{applicationId}")
    @Operation(summary = "Get job application by ID")
    @PreAuthorize("hasRole('JOBSEEKER')")
    @ResponseStatus(HttpStatus.OK)
    public Mono<JobApplicationResponseDTO> getJobApplicationById(@PathVariable("applicationId") UUID applicationId,
                                                                 Authentication authentication) {
        return Mono.just(jobApplicationService.getJobApplicationById(applicationId, authentication));
    }

    @DeleteMapping("/delete-application/{applicationId}")
    @Operation(summary = "Delete a job application")
    @PreAuthorize("hasRole('JOBSEEKER')")
    @ResponseStatus(HttpStatus.OK)
    public Mono<String> deleteJobApplication(@PathVariable("applicationId") UUID applicationId,
                                             Authentication authentication) {
        return Mono.just(jobApplicationService.deleteJobApplication(applicationId, authentication));
    }

    @GetMapping("/get-applications")
    @Operation(summary = "Get all job applications by Job Seeker")
    @PreAuthorize("hasRole('JOBSEEKER')")
    @ResponseStatus(HttpStatus.OK)
    public Flux<JobApplyResponseDTO> getJobApplicationByJobSeekerId(Authentication authentication) {
        return Flux.fromIterable(jobApplicationService.getJobApplicationByJobSeekerId(authentication));
    }

}
