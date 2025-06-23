package com.jobconnect.user.controller;

import com.jobconnect.user.dto.JobSeekerRequestDTO;
import com.jobconnect.user.dto.JobSeekerResponseDTO;
import com.jobconnect.user.service.JobSeekerService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/jobseeker")
@Tag(name = "JobSeeker Controller Api's", description = "JobSeeker related operations")
public class JobSeekerController {

    private final JobSeekerService jobSeekerService;
    public JobSeekerController(JobSeekerService jobSeekerService) {
        this.jobSeekerService = jobSeekerService;
    }

    @GetMapping("/api/v1/jobseeker")
    @Tag(name = "Get JobSeeker", description = "Get JobSeeker details by UserId")
    @PreAuthorize("hasRole('JOBSEEKER')")
    @ResponseStatus(HttpStatus.OK)
    public Mono<JobSeekerResponseDTO> getJobSeeker(Authentication authentication) {
        UUID userId = (UUID) authentication.getPrincipal();
        return Mono.just(jobSeekerService.getJobSeeker(userId));
    }

    @PutMapping("/update")
    @Tag(name = "Update JobSeeker", description = "Update JobSeeker details by UserId")
    @PreAuthorize("hasRole('JOBSEEKER')")
    @ResponseStatus(HttpStatus.OK)
    public Mono<JobSeekerResponseDTO> updateJobSeeker(@Valid @RequestBody JobSeekerRequestDTO jobSeekerRequestDTO,
                                                      Authentication authentication){
        return Mono.just(jobSeekerService.updateJobSeeker(jobSeekerRequestDTO, authentication));
    }

    @DeleteMapping("/delete-account")
    @Tag(name = "Delete JobSeeker", description = "Delete JobSeeker by UserId")
    @PreAuthorize("hasRole('JOBSEEKER')")
    @ResponseStatus(HttpStatus.OK)
    public Mono<String> deleteJobSeeker(Authentication authentication) {
        UUID userId = (UUID) authentication.getPrincipal();
        return Mono.just(jobSeekerService.deleteJobSeeker(userId));
    }

    @PostMapping("/upload-resume")
    @Tag(name = "Upload Resume", description = "Upload JobSeeker's resume")
    @PreAuthorize("hasRole('JOBSEEKER')")
    @ResponseStatus(HttpStatus.OK)
    public Mono<String> uploadResume(@RequestParam("file") @Valid @Size(max = 10485760) MultipartFile file, Authentication authentication) {
        return Mono.just(jobSeekerService.uploadResume(file, authentication));
    }

    @DeleteMapping("/delete-resume")
    @Tag(name = "Delete Resume", description = "Delete JobSeeker's resume")
    @PreAuthorize("hasRole('JOBSEEKER')")
    @ResponseStatus(HttpStatus.OK)
    public Mono<String> deleteResume(Authentication authentication) {
        return Mono.just(jobSeekerService.deleteResume(authentication));
    }
}
