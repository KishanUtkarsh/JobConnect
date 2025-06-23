package com.jobconnect.application.controller;
import com.jobconnect.application.dto.JobApplicationUpdateDTO;
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

@RequestMapping("/api/v1/jobApplication-recruiter")
@RestController
@Tag(name = "JobApplication Recruiter Controller Api's", description = "JobApplication Recruiter related operations")
public class JobApplicationRecruiterController {

    private final JobApplicationServiceImpl jobApplicationService;
    public JobApplicationRecruiterController(JobApplicationServiceImpl jobApplicationService) {
        this.jobApplicationService = jobApplicationService;
    }

    @GetMapping("/get-applications")
    @Operation(summary = "Get all job applications by Job ID")
    @PreAuthorize("hasRole('RECRUITER')")
    @ResponseStatus(HttpStatus.OK)
    public Flux<JobApplyResponseDTO> getJobApplicationByJobId(@RequestParam("jobId") UUID jobId,
                                                               Authentication authentication) {
        return Flux.fromIterable(jobApplicationService.getJobApplicationByJobId(jobId, authentication));
    }

    @PutMapping("/update-status")
    @Operation(summary = "Update job application status")
    @PreAuthorize("hasRole('RECRUITER')")
    @ResponseStatus(HttpStatus.OK)
    public Mono<String> updateJobApplicationStatus(@RequestBody JobApplicationUpdateDTO dto,
                                                    Authentication authentication) {
        return Mono.just(jobApplicationService.updateJobApplicationStatus(dto, authentication));
    }

}
