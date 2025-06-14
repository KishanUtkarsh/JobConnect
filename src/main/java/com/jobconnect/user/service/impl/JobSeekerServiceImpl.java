package com.jobconnect.user.service.impl;

import com.jobconnect.auth.entity.User;
import com.jobconnect.common.exception.UserNotFoundException;
import com.jobconnect.common.util.UserMapperUtil;
import com.jobconnect.repository.JobSeekerRepository;
import com.jobconnect.repository.UserRepository;
import com.jobconnect.user.dto.JobSeekerRequestDTO;
import com.jobconnect.user.dto.JobSeekerResponseDTO;
import com.jobconnect.user.service.JobSeekerService;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
public class JobSeekerServiceImpl implements JobSeekerService {

    private final JobSeekerRepository jobSeekerRepository;
    private final UserRepository userRepository;
    public JobSeekerServiceImpl(JobSeekerRepository jobSeekerRepository, UserRepository userRepository) {
        this.jobSeekerRepository = jobSeekerRepository;
        this.userRepository = userRepository;
    }

    @Override
    public JobSeekerResponseDTO getJobSeeker(UUID userId) {
        var jobSeeker = jobSeekerRepository.findJobSeekerByUser(userId);

        log.info("JobSeeker found with UserId: {}", jobSeeker.getId() + " : " + jobSeeker.getUser().getFirstName());
        return UserMapperUtil.convertToJobSeekerResponse(jobSeeker);
    }

    @Override
    @Transactional
    public JobSeekerResponseDTO updateJobSeeker(UUID userId, JobSeekerRequestDTO jobSeekerRequestDTO) {
        var jobSeeker = jobSeekerRepository.findJobSeekerByUser(userId);
        var updatedJobSeeker = UserMapperUtil.updateJobSeeker(jobSeeker, jobSeekerRequestDTO, "");
        jobSeekerRepository.save(updatedJobSeeker);

        log.info("Updated JobSeeker with UserId: {}", updatedJobSeeker.getId());
        return UserMapperUtil.convertToJobSeekerResponse(updatedJobSeeker);
    }

    @Override
    @Transactional
    public String deleteJobSeeker(UUID userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new UserNotFoundException(userId)
        );

        user.setActive(false);
        userRepository.save(user);
        log.info("JobSeeker deleted jobseeker with UserId: {}", user.getId());
        return "JobSeeker has been deleted for UserId" + userId;
    }
}
