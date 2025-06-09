package com.jobconnect.auth.service.impl;

import com.jobconnect.auth.dto.UserRequest;
import com.jobconnect.auth.entity.User;
import com.jobconnect.auth.security.SecurityConfig;
import com.jobconnect.auth.service.RoleService;
import com.jobconnect.auth.service.UserService;
import com.jobconnect.common.util.JwtUtil;
import com.jobconnect.common.util.OtpUtil;
import com.jobconnect.common.util.UserUtil;
import com.jobconnect.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final RoleService roleService;
    private final UserRepository userRepository;

    public UserServiceImpl(RoleService roleService, UserRepository userRepository) {
        this.roleService = roleService;
        this.userRepository = userRepository;
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepository.findUserByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("User not found with email: " + email));
    }

    @Override
    public User registerUser(UserRequest userRequest) {
        User newUser = UserUtil.convertToUser(userRequest, roleService.getRoleByName(userRequest.role()));
        return userRepository.save(newUser);
    }

    @Override
    public String verifyUser(String email, String password) {
        User user = getUserByEmail(email);

        return SecurityConfig.passwordEncoder().matches(password, user.getPassword())
                ? OtpUtil.generateOtp(user.getTotpSecret())
                : "Incorrect password";
    }

    @Override
    public String verifyOtp(String email, String otp) {
        User user = getUserByEmail(email);
        return OtpUtil.validateOtp(user.getTotpSecret(), otp) ? JwtUtil.generateJwtToken(user.getEmail(), String.valueOf(user.getRole().getName()), "new" ) : "Invalid OTP";
    }

}
