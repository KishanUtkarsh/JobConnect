package com.jobconnect.user.service.impl;

import com.jobconnect.auth.entity.User;
import com.jobconnect.common.exception.UserNotFoundException;
import com.jobconnect.common.util.UserMapperUtil;
import com.jobconnect.repository.RecruiterRepository;
import com.jobconnect.repository.UserRepository;
import com.jobconnect.user.dto.RecruiterRequestDTO;
import com.jobconnect.user.dto.RecruiterResponseDTO;
import com.jobconnect.user.service.RecruiterService;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
public class RecruiterServiceImpl implements RecruiterService {

    private final RecruiterRepository recruiterRepository;
    private final UserRepository userRepository;

    public RecruiterServiceImpl(RecruiterRepository recruiterRepository, UserRepository userRepository) {
        this.recruiterRepository = recruiterRepository;
        this.userRepository = userRepository;
    }

    @Override
    public RecruiterResponseDTO getRecruiter(UUID userId) {
        var recruiter = recruiterRepository.findRecruiterByUserId(userId);
        log.info("Recruiter found recruiter: {}", recruiter.getId()+ " : " + recruiter.getUser().getFirstName());
        return UserMapperUtil.convertToRecruiterResponse(recruiter);
    }

    @Override
    @Transactional
    public RecruiterResponseDTO updateRecruiter(RecruiterRequestDTO recruiterRequestDTO, Authentication authentication) {
        UUID userId = (UUID) authentication.getPrincipal();
        var recruiter = recruiterRepository.findRecruiterByUserId(userId);
        var updatedRecruiter = UserMapperUtil.updateRecruiter(recruiter,recruiterRequestDTO);
        log.info("Recruiter Updated with recruiterId : {}", recruiter.getId());
        return UserMapperUtil.convertToRecruiterResponse(updatedRecruiter);
    }

    @Override
    @Transactional
    public String deleteRecruiter(UUID userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new UserNotFoundException(userId)
        );

        user.setActive(false);
        userRepository.save(user);
        log.info("Recruiter Disabled recruiter with UserId: {}", user.getId());
        return "Recruiter has been disable for UserId" + userId + " and deleted in 14 days";
    }
}
