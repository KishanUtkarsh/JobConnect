package com.jobconnect.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.UUID;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(UUID userId) {
        super("User not found with userId: " + userId);
    }

    public UserNotFoundException(String email) {
        super("User not found with email" + email);
    }
}
