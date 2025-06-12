package com.jobconnect.common.dto;

import org.springframework.http.HttpStatus;

import java.time.Instant;

public record ErrorResponseDTO(
        Instant timestamp,
        int status,
        String error,
        String message,
        String path
) {

    public ErrorResponseDTO(HttpStatus status, String message, String path) {
        this(Instant.now(), status.value(), status.getReasonPhrase(), message, path);
    }

}
