package com.jobconnect.common.util;

import com.jobconnect.auth.dto.UserRequestDTO;
import com.jobconnect.auth.dto.UserResponseDTO;
import com.jobconnect.auth.entity.Role;
import com.jobconnect.auth.entity.User;
import com.jobconnect.config.jwt.SecurityConfig;
import com.jobconnect.user.dto.JobSeekerRequestDTO;
import com.jobconnect.user.dto.JobSeekerResponseDTO;
import com.jobconnect.user.dto.RecruiterRequestDTO;
import com.jobconnect.user.dto.RecruiterResponseDTO;
import com.jobconnect.user.entity.JobSeeker;
import com.jobconnect.user.entity.Recruiter;

public class UserMapperUtil {

    private UserMapperUtil() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    // Auth User Mapper Functions

    public static User convertToUser(UserRequestDTO userRequest , Role role) {
        User user = new User();
        user.setFirstName(userRequest.firstName());
        user.setLastName(userRequest.lastName());
        user.setEmail(userRequest.email());
        user.setPassword(SecurityConfig.passwordEncoder().encode(userRequest.password()));
        user.setTotpSecret(OtpUtil.generateOtpSecret());
        user.setActive(true);
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

    // Users (Recruiter,JobSeeker) Mapper Functions

    public static Recruiter updateRecruiter(Recruiter oldRecruiter, RecruiterRequestDTO recruiterRequestDTO){
        oldRecruiter.setCompanyName(recruiterRequestDTO.companyName());
        oldRecruiter.setPosition(recruiterRequestDTO.position());
        oldRecruiter.setCompanyUrl(recruiterRequestDTO.companyUrl());
        return oldRecruiter;
    }

    public static RecruiterResponseDTO convertToRecruiterResponse(Recruiter recruiter){
        return new RecruiterResponseDTO(
                recruiter.getId(),
                recruiter.getUser().getId(),
                recruiter.getCompanyName(),
                recruiter.getPosition(),
                recruiter.getCompanyUrl()
        );
    }

    public static JobSeekerResponseDTO convertToJobSeekerResponse(JobSeeker jobSeeker, String resumeUrl) {
        return new JobSeekerResponseDTO(
                jobSeeker.getId(),
                jobSeeker.getUser().getId(),
                resumeUrl,
                jobSeeker.getSkills(),
                jobSeeker.getDegree(),
                jobSeeker.getEmploymentType()
        );
    }

    public static JobSeeker updateJobSeeker(JobSeeker oldJobSeeker, JobSeekerRequestDTO jobSeekerRequestDTO, String resumeUrl){
        oldJobSeeker.setDegree(jobSeekerRequestDTO.degree());
        oldJobSeeker.setEmploymentType(jobSeekerRequestDTO.employmentType());
        oldJobSeeker.setSkills(jobSeekerRequestDTO.skills());
        oldJobSeeker.setResume(resumeUrl);
        return oldJobSeeker;
    }
}
