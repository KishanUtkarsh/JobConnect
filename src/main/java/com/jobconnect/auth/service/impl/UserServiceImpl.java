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
import com.jobconnect.common.util.UserMapperUtil;
import com.jobconnect.repository.JobSeekerRepository;
import com.jobconnect.repository.RecruiterRepository;
import com.jobconnect.repository.UserRepository;
import com.jobconnect.user.entity.JobSeeker;
import com.jobconnect.user.entity.Recruiter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    private final RoleService roleService;
    private final UserRepository userRepository;
    private final JobSeekerRepository jobSeekerRepository;
    private final RecruiterRepository recruiterRepository;

    public UserServiceImpl(RoleService roleService, UserRepository userRepository, JobSeekerRepository jobSeekerRepository, RecruiterRepository recruiterRepository) {
        this.roleService = roleService;
        this.userRepository = userRepository;
        this.jobSeekerRepository = jobSeekerRepository;
        this.recruiterRepository = recruiterRepository;
    }

    @Override
    public UserResponseDTO getUserByEmail(String email) {
        User user = userRepository.findUserByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(email));
        return UserMapperUtil.convertToUserResponse(user);
    }

    @Override
    public UserResponseDTO registerUser(UserRequestDTO userRequest) {
        User user = UserMapperUtil.convertToUser(userRequest, roleService.getRoleByName(userRequest.role()));
        User savedUser = userRepository.save(user);
        
        String role = savedUser.getRole().toString();
        if(role.equals("ROLE_JOBSEEKER")){
            JobSeeker jobSeeker = new JobSeeker();
            jobSeeker.setUser(savedUser);
            jobSeekerRepository.save(jobSeeker);
            
        } else if (role.equals("ROLE_RECRUITER")) {
            Recruiter recruiter = new Recruiter();
            recruiter.setUser(savedUser);
            recruiterRepository.save(recruiter);
        }

        return UserMapperUtil.convertToUserResponse(savedUser);
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

        String jwtToken = JwtUtil.generateJwtToken(user.getId(), user.getRole().getName().toString(), "new");
        log.info("Generated JWT token for user: {}", user.getEmail());

        return new AuthResponseDTO(jwtToken,"Bearer", JwtUtil.getAccessTokenExpiration(jwtToken));
    }

    @Override
    public List<UserResponseDTO> getAllUsers(Pageable pageable) {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(UserMapperUtil::convertToUserResponse)
                .toList();
    }

}
