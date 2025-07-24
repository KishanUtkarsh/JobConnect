package com.jobconnect.common.exception;

import com.jobconnect.common.dto.ErrorResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerWebExchange;

import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.time.Instant;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> handleUserNotFoundException(UserNotFoundException ex, ServerWebExchange exchange) {
        return buildErrorResponse(HttpStatus.NOT_FOUND, ex.getMessage(), exchange);
    }

    @ExceptionHandler(InvalidOtpException.class)
    public ResponseEntity<ErrorResponseDTO> handleInvalidOtpException(InvalidOtpException ex, ServerWebExchange exchange) {
        return buildErrorResponse(HttpStatus.UNAUTHORIZED, ex.getMessage(), exchange);
    }

    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<ErrorResponseDTO> handleInvalidCredentialsException(InvalidCredentialsException ex, ServerWebExchange exchange) {
        return buildErrorResponse(HttpStatus.UNAUTHORIZED, ex.getMessage(), exchange);
    }

    @ExceptionHandler(InvalidJwtTokenException.class)
    public ResponseEntity<ErrorResponseDTO> handleInvalidJwtTokenException(InvalidJwtTokenException ex, ServerWebExchange exchange) {
        return buildErrorResponse(HttpStatus.UNAUTHORIZED, ex.getMessage(), exchange);
    }

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ErrorResponseDTO> handleResponseStatusException(ResponseStatusException ex, ServerWebExchange exchange) {
        return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage(), exchange);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> handleResourceNotFoundException(ResourceNotFoundException ex, ServerWebExchange exchange) {
        return buildErrorResponse(HttpStatus.NOT_FOUND, ex.getMessage(), exchange);
    }

    @ExceptionHandler(AuthorizationDeniedException.class)
    public ResponseEntity<ErrorResponseDTO> handleAuthorizationDeniedException(AuthorizationDeniedException ex, ServerWebExchange exchange) {
        log.warn("Authorization denied at {}: {}", Instant.now(), ex.getMessage());
        return buildErrorResponse(HttpStatus.FORBIDDEN, "Access denied", exchange);
    }

    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<ErrorResponseDTO> handleConflictException(ConflictException ex, ServerWebExchange exchange) {
        log.warn("Conflict error at {}: {}", Instant.now(), ex.getMessage());
        return buildErrorResponse(HttpStatus.CONFLICT, ex.getMessage(), exchange);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponseDTO> handleIllegalArgumentException(IllegalArgumentException ex, ServerWebExchange exchange) {
        log.warn("Illegal argument at {}: {}", Instant.now(), ex.getMessage());
        return buildErrorResponse(HttpStatus.BAD_REQUEST, ex.getMessage(), exchange);
    }

    @ExceptionHandler(PermissionDeniedException.class)
    public ResponseEntity<ErrorResponseDTO> handlePermissionDeniedException(PermissionDeniedException ex, ServerWebExchange exchange) {
        log.warn("Permission denied at {}: {}", Instant.now(), ex.getMessage());
        return buildErrorResponse(HttpStatus.UNAUTHORIZED, ex.getMessage(), exchange);
    }

    @ExceptionHandler(FileNotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> handleFileNotFoundException(FileNotFoundException ex, ServerWebExchange exchange) {
        log.warn("File not found at {}: {}", Instant.now(), ex.getMessage());
        return buildErrorResponse(HttpStatus.NOT_FOUND, ex.getMessage(), exchange);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorResponseDTO> handleAccessDeniedException(AccessDeniedException ex, ServerWebExchange exchange) {
        log.warn("Access denied at {}: {}", Instant.now(), ex.getMessage());
        return buildErrorResponse(HttpStatus.FORBIDDEN, "Access denied", exchange);
    }

    @ExceptionHandler(FileUploadException.class)
    public ResponseEntity<ErrorResponseDTO> handleFileUploadException(FileUploadException ex, ServerWebExchange exchange) {
        log.warn("File upload error at {}: {}", Instant.now(), ex.getMessage());
        return buildErrorResponse(HttpStatus.BAD_REQUEST, ex.getMessage(), exchange);
    }
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponseDTO> handleDataIntegrityViolationException(DataIntegrityViolationException ex, ServerWebExchange exchange) {
        log.error("Data integrity violation at {}: {}", Instant.now(), ex.getMessage(), ex);
        return buildErrorResponse(HttpStatus.CONFLICT, "Data integrity violation", exchange);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDTO> handleGlobalException(Exception ex, ServerWebExchange exchange) {
        log.error("An unexpected error occurred at {}: {}", Instant.now(), ex.getMessage(), ex);
        return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "An unexpected error occurred", exchange);
    }

    @ExceptionHandler(WebClientResponseException.InternalServerError.class)
    public ResponseEntity<ErrorResponseDTO> handleWebClientInternalServerError(WebClientResponseException.InternalServerError ex, ServerWebExchange exchange) {
        log.error("WebClient Internal Server Error at {}: {}", Instant.now(), ex.getMessage(), ex);
        return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage(), exchange);
    }

    @ExceptionHandler(IOException.class)
    public ResponseEntity<ErrorResponseDTO> handleIOException(IOException ex, ServerWebExchange exchange) {
        log.error("I/O error at {}: {}", Instant.now(), ex.getMessage(), ex);
        return buildErrorResponse(HttpStatus.NO_CONTENT, "I/O error occurred", exchange);
    }

    private ResponseEntity<ErrorResponseDTO> buildErrorResponse(HttpStatus status, String message, ServerWebExchange exchange) {
        String path = exchange.getRequest().getPath().value();

        if(message.contains("404 NOT_FOUND")) {
            message = "Resource / Api not found";
            status = HttpStatus.NOT_FOUND;
        } else if (message.contains("405 METHOD_NOT_ALLOWED")) {
            message = "Specified method is not allowed for this resource";
            status = HttpStatus.METHOD_NOT_ALLOWED;
        }

        ErrorResponseDTO response = new ErrorResponseDTO(
                status,
                message,
                path
        );
        return new ResponseEntity<>(response, status);
    }
}
