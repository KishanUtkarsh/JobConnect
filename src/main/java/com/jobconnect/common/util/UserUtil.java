package com.jobconnect.common.util;

import com.jobconnect.auth.dto.UserRequestDTO;
import com.jobconnect.auth.dto.UserResponseDTO;
import com.jobconnect.auth.entity.Role;
import com.jobconnect.auth.entity.User;
import com.jobconnect.config.jwt.SecurityConfig;

import java.util.Date;

public class UserUtil {

    private  UserUtil() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    public static User convertToUser(UserRequestDTO userRequest , Role role) {
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

    public static UserResponseDTO convertToUserResponse(User user){
        return new UserResponseDTO(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getRole().getName().toString(),
                user.isActive() ? "Active" : "Inactive"
        );
    }

}
