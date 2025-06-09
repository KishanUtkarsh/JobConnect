package com.jobconnect.auth.controller;

import com.jobconnect.auth.dto.UserRequest;
import com.jobconnect.auth.entity.User;
import com.jobconnect.auth.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.CREATED)
    public User registerUser(@RequestBody UserRequest userRequest) {
        return userService.registerUser(userRequest);
    }

    @GetMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public String login(@RequestParam String email, @RequestParam String password) {
        return userService.verifyUser(email, password);
    }

    @GetMapping("/verify-otp")
    @ResponseStatus(HttpStatus.OK)
    public String verifyOtp(@RequestParam String email, @RequestParam String otp) {
        return userService.verifyOtp(email, otp);
    }

}
