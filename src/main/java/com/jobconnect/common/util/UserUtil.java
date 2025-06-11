package com.jobconnect.common.util;

import com.jobconnect.auth.dto.UserRequest;
import com.jobconnect.auth.dto.UserResponse;
import com.jobconnect.auth.entity.Role;
import com.jobconnect.auth.entity.User;
import com.jobconnect.config.SecurityConfig;

import java.util.Date;

public class UserUtil {

    private  UserUtil() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    public static User convertToUser(UserRequest userRequest , Role role) {
        User user = new User();
        user.setFirstName(userRequest.firstName());
        user.setLastName(userRequest.lastName());
        user.setEmail(userRequest.email());
        user.setPassword(SecurityConfig.passwordEncoder().encode(userRequest.password()));
        user.setTotpSecret(OtpUtil.generateOtpSecret());
        user.setActive(true);
        user.setCreatedAt(new Date());
        user.setRole(role);
        return user;
    }

    public static UserResponse convertToUserResponse(User user){
        return new UserResponse(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getRole().getName().toString(),
                user.isActive() ? "Active" : "Inactive"
        );
    }

}
