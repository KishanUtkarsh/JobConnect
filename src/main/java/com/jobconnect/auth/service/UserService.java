package com.jobconnect.auth.service;

import com.jobconnect.auth.dto.*;
import java.util.List;
import org.springframework.data.domain.Pageable;

public interface UserService {

    UserResponse registerUser(UserRequest userRequest);

    UserResponse getUserByEmail(String email);

    LoginResponse verifyUser(LoginRequest loginRequest);

    AuthResponse verifyOtp(OtpRequest otpRequest);

    List<UserResponse> getAllUsers(Pageable pageable);
}
