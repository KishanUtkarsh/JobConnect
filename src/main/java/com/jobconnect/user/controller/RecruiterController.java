package com.jobconnect.user.controller;

import com.jobconnect.user.dto.RecruiterRequestDTO;
import com.jobconnect.user.dto.RecruiterResponseDTO;
import com.jobconnect.user.service.RecruiterService;
import com.jobconnect.user.service.impl.RecruiterServiceImpl;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/recruiter")
@Tag(name = "Recruiter controller Api's", description = "Recruiter related operations")
public class RecruiterController {

    private final RecruiterServiceImpl recruiterService;
    public RecruiterController(RecruiterServiceImpl recruiterService) {
        this.recruiterService = recruiterService;
    }

    @GetMapping("/")
    @Tag(name = "Get Recruiter", description = "Get Recruiter details by UserId")
    @PreAuthorize("hasRole('RECRUITER')")
    @ResponseStatus(HttpStatus.OK)
    public Mono<RecruiterResponseDTO> getRecruiter(Authentication authentication) {
        UUID userId = (UUID) authentication.getPrincipal();
        return Mono.just(recruiterService.getRecruiter(userId));
    }

    @PutMapping("/update")
    @Tag(name = "Update Recruiter", description = "Update Recruiter details by UserId")
    @PreAuthorize("hasRole('RECRUITER')")
    @ResponseStatus(HttpStatus.OK)
    public Mono<RecruiterResponseDTO> updateRecruiter(@Valid @RequestBody RecruiterRequestDTO recruiterRequestDTO,
                                                      Authentication authentication) {
        return Mono.just(recruiterService.updateRecruiter(recruiterRequestDTO, authentication));

    }

    @DeleteMapping("/delete-account")
    @Tag(name = "Delete Recruiter", description = "Delete Recruiter by UserId")
    @PreAuthorize("hasRole('RECRUITER')")
    @ResponseStatus(HttpStatus.OK)
    public Mono<String> deleteRecruiter(Authentication authentication) {
        UUID userId = (UUID) authentication.getPrincipal();
        return Mono.just(recruiterService.deleteRecruiter(userId));
    }

}
