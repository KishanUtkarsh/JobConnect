package com.jobconnect.job.controller;


import com.jobconnect.job.dto.JobDto;
import com.jobconnect.job.entity.Job;
import com.jobconnect.job.service.JobService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/public/job")
@Tag(name = "Public Job API", description = "Endpoints for public access to job postings")
public class JobPublicController {

    private final JobService jobService;
    public JobPublicController(JobService jobService) {
        this.jobService = jobService;
    }

    @GetMapping( "/get-allJobs")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get all jobs", description = "Fetches a list of all available job postings.")
    public Flux<JobDto> getAllJobs(
            @RequestParam (required = false) Set<String> skills,
            @RequestParam (required = false) String location,
            @RequestParam (required = false) String jobType,
            @RequestParam (required = false) String keyword,
            @RequestParam (defaultValue = "0") int page,
            @RequestParam (defaultValue = "10") int size
    ) {
        return Flux.fromIterable(
                jobService.findAllJobs(skills, location, jobType, keyword, page, size)
        );
    }

}
