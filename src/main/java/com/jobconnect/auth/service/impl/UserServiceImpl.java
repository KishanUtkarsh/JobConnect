package com.jobconnect.auth.service.impl;

import com.jobconnect.auth.dto.*;
import com.jobconnect.auth.entity.User;
import com.jobconnect.common.exception.InvalidCredentialsException;
import com.jobconnect.common.exception.InvalidOtpException;
import com.jobconnect.common.exception.UserNotFoundException;
import com.jobconnect.config.jwt.SecurityConfig;
import com.jobconnect.auth.service.RoleService;
import com.jobconnect.auth.service.UserService;
import com.jobconnect.common.util.JwtUtil;
import com.jobconnect.common.util.OtpUtil;
import com.jobconnect.common.util.UserUtil;
import com.jobconnect.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    private final RoleService roleService;
    private final UserRepository userRepository;

    public UserServiceImpl(RoleService roleService, UserRepository userRepository) {
        this.roleService = roleService;
        this.userRepository = userRepository;
    }

    @Override
    public UserResponseDTO getUserByEmail(String email) {
        User user = userRepository.findUserByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(email));
        return UserUtil.convertToUserResponse(user);
    }

    @Override
    public UserResponseDTO registerUser(UserRequestDTO userRequest) {
        User user = UserUtil.convertToUser(userRequest, roleService.getRoleByName(userRequest.role()));
        User savedUser = userRepository.save(user);
        return UserUtil.convertToUserResponse(savedUser);
    }

    @Override
    public LoginResponseDTO verifyUser(LoginRequestDTO loginRequest) {

        User user = userRepository.findUserByEmail(loginRequest.email()).orElseThrow(
                () -> new UserNotFoundException(loginRequest.email())
        );

        if(!SecurityConfig.passwordEncoder().matches(loginRequest.password(), user.getPassword())) {
            throw new InvalidCredentialsException();
        }
        String otp = OtpUtil.generateOtp(user.getTotpSecret());
        return new LoginResponseDTO(user.getEmail(), otp);
    }

    @Override
    public AuthResponseDTO verifyOtp(OtpRequestDTO otpRequest) {

        User user = userRepository.findUserByEmail(otpRequest.email()).orElseThrow(
                () -> new UserNotFoundException(otpRequest.email())
        );

        if(!OtpUtil.validateOtp(user.getTotpSecret(),otpRequest.otp())){
            throw new InvalidOtpException();
        }

        String jwtToken = JwtUtil.generateJwtToken(user.getEmail(), user.getRole().getName().toString(), "new");
        log.info("Generated JWT token for user: {}", user.getEmail());

        return new AuthResponseDTO(jwtToken,"Bearer", JwtUtil.getAccessTokenExpiration(jwtToken));
    }

    @Override
    public List<UserResponseDTO> getAllUsers(Pageable pageable) {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(UserUtil::convertToUserResponse)
                .toList();
    }

}
