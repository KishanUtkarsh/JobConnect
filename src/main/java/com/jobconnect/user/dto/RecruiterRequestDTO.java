package com.jobconnect.user.dto;

import jakarta.validation.constraints.NotBlank;

public record RecruiterRequestDTO(
        @NotBlank String companyName,
        @NotBlank String position,
        @NotBlank String companyUrl

) {
}
