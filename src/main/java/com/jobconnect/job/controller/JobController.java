package com.jobconnect.job.controller;

import com.jobconnect.job.dto.JobDto;
import com.jobconnect.job.service.JobService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
    public String createJob() {
        // Placeholder for future implementation
        return "Job creation endpoint will be implemented here.";
    }

    @PutMapping("delete-job/{jobId}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('RECRUITER')")
    public Mono<String> deleteJob(@PathVariable UUID jobId) {
        return Mono.just(jobService.deleteJob(jobId));
    }

    @PutMapping("update-job")
    @PreAuthorize("hasRole('RECRUITER')")
    public String updateJob(@RequestBody JobDto jobDto) {
        // Placeholder for future implementation
        return "Job update endpoint for job ID ";
    }

}
