package com.jobconnect.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "File upload failed")
public class FileUploadException extends RuntimeException {
    public FileUploadException(String message) {
        super(message);
    }
}
