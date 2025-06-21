package com.jobconnect.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class PermissionDeniedException extends RuntimeException {
    public PermissionDeniedException(String message) {
        super(message);
    }
}
