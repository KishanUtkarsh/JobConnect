package com.jobconnect.auth.service;

import com.jobconnect.auth.dto.UserRequest;
import com.jobconnect.auth.entity.User;

public interface UserService {

    User registerUser(UserRequest userRequest);

    User getUserByEmail(String email);

    String verifyUser(String email, String password);

    String verifyOtp(String email, String otp);
}
