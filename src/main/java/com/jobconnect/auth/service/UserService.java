package com.jobconnect.auth.service;

import com.jobconnect.auth.dto.*;
import java.util.List;
import org.springframework.data.domain.Pageable;

public interface UserService {

    UserResponseDTO registerUser(UserRequestDTO userRequest);

    UserResponseDTO getUserByEmail(String email);

    LoginResponseDTO verifyUser(LoginRequestDTO loginRequest);

    AuthResponseDTO verifyOtp(OtpRequestDTO otpRequest);

    List<UserResponseDTO> getAllUsers(Pageable pageable);
}
