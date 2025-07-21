package com.jobconnect.job.controller;

import com.jobconnect.job.dto.JobRequestDto;
import com.jobconnect.job.dto.JobResponseDto;
import com.jobconnect.job.service.JobService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.UUID;


@RestController
@RequestMapping("/api/v1/job")
@Tag(name = "Job Management API", description = "Endpoints for managing job postings and applications")
public class JobController {

    private final JobService jobService;
    public JobController(JobService jobService) {
        this.jobService = jobService;
    }

    @PostMapping("/create-job")
    @PreAuthorize("hasRole('RECRUITER')")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create a new job", description = "Allows recruiters to create a new job posting.")
    public Mono<JobResponseDto> createJob(@Valid @RequestBody JobRequestDto jobDto, Authentication authentication) {
        return Mono.just(jobService.createJob(jobDto, authentication));
    }

    @DeleteMapping("/delete-job")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('RECRUITER')")
    @Operation(summary = "Delete a job", description = "Allows recruiters to delete a job posting by its ID.")
    public Mono<String> deleteJob(@RequestParam("jobId") UUID jobId, Authentication authentication) {
        return Mono.just(jobService.deleteJob(jobId, authentication));
    }

    @PutMapping("/update-job")
    @PreAuthorize("hasRole('RECRUITER')")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Update a job", description = "Allows recruiters to update an existing job posting.")
    public Mono<JobResponseDto> updateJob(@Valid @RequestBody JobRequestDto jobDto, Authentication authentication) {
        return Mono.just(jobService.updateJob(jobDto, authentication));
    }

}
