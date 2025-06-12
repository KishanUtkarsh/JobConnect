package com.jobconnect.auth.controller;

import com.jobconnect.auth.dto.*;
import com.jobconnect.auth.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/v1/auth")
@Tag(name = "User Authentication API" , description = "Endpoints for user registration and authentication")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/signup")
    @Operation(summary = "Register a new user", description = "Endpoint to register a new user with email and password")
    public ResponseEntity<UserResponseDTO> registerUser(@Valid @RequestBody UserRequestDTO userRequest) {
        log.info("Registering user with email: {}", userRequest.email());
        return new ResponseEntity<>(userService.registerUser(userRequest), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    @Operation(summary = "User login", description = "Endpoint for user login with email and password")
    public ResponseEntity<LoginResponseDTO> login(@Valid @RequestBody LoginRequestDTO loginRequest) {
        log.info("User login attempt for email: {}", loginRequest.email());
        return ResponseEntity.ok(userService.verifyUser(loginRequest));
    }

    @PostMapping("/verify-otp")
    @Operation(summary = "Verify OTP", description = "Endpoint to verify the OTP sent with the user's email")
    public ResponseEntity<AuthResponseDTO> verifyOtp(@Valid @RequestBody OtpRequestDTO request) {
        log.info("Verifying OTP for email: {}", request.email());
        return ResponseEntity.ok(userService.verifyOtp(request));
    }

}
