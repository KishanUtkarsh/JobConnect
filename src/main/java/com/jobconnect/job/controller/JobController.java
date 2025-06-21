package com.jobconnect.job.controller;

import com.jobconnect.job.dto.JobDto;
import com.jobconnect.job.service.JobService;
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

    @PostMapping("create-job")
    @PreAuthorize("hasRole('RECRUITER')")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<JobDto> createJob(@Valid @RequestBody JobDto jobDto, Authentication authentication) {
        return Mono.just(jobService.createJob(jobDto, authentication));
    }

    @DeleteMapping("delete-job/{jobId}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('RECRUITER')")
    public Mono<String> deleteJob(@PathVariable UUID jobId, Authentication authentication) {
        return Mono.just(jobService.deleteJob(jobId, authentication));
    }

    @PutMapping("update-job")
    @PreAuthorize("hasRole('RECRUITER')")
    @ResponseStatus(HttpStatus.OK)
    public Mono<JobDto> updateJob(@Valid @RequestBody JobDto jobDto, Authentication authentication) {
        return Mono.just(jobService.updateJob(jobDto, authentication));
    }

}
