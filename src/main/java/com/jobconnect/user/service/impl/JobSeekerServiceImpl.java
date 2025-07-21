package com.jobconnect.user.service.impl;

import com.jobconnect.auth.entity.User;
import com.jobconnect.common.exception.FileNotFoundException;
import com.jobconnect.common.exception.UserNotFoundException;
import com.jobconnect.common.storage.FileStorageService;
import com.jobconnect.common.util.UserMapperUtil;
import com.jobconnect.repository.JobSeekerRepository;
import com.jobconnect.repository.UserRepository;
import com.jobconnect.user.dto.JobSeekerRequestDTO;
import com.jobconnect.user.dto.JobSeekerResponseDTO;
import com.jobconnect.user.entity.JobSeeker;
import com.jobconnect.user.service.JobSeekerService;
import com.jobconnect.user.service.RecruiterService;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Service
@Slf4j
public class JobSeekerServiceImpl implements JobSeekerService {

    private final JobSeekerRepository jobSeekerRepository;
    private final UserRepository userRepository;
    private final FileStorageService fileStorageService;

    public JobSeekerServiceImpl(JobSeekerRepository jobSeekerRepository, UserRepository userRepository, FileStorageService fileStorageService) {
        this.jobSeekerRepository = jobSeekerRepository;
        this.userRepository = userRepository;
        this.fileStorageService = fileStorageService;
    }

    @Override
    public JobSeekerResponseDTO getJobSeeker(UUID userId) {
        JobSeeker jobSeeker = jobSeekerRepository.findJobSeekerByUserId(userId);

        log.info("JobSeeker found with UserId: {}", jobSeeker.getId() + " : " + jobSeeker.getUser().getFirstName());
        String resumeUrl = "Resume not uploaded";
        if (jobSeeker.getResume() != null && !jobSeeker.getResume().isEmpty()) {
            resumeUrl = fileStorageService.getFileUrl(jobSeeker.getResume());
        }
        return UserMapperUtil.convertToJobSeekerResponse(jobSeeker, resumeUrl);
    }

    @Override
    @Transactional
    public JobSeekerResponseDTO updateJobSeeker(JobSeekerRequestDTO jobSeekerRequestDTO, Authentication authentication) {
        UUID userId = (UUID) authentication.getPrincipal();
        var jobSeeker = jobSeekerRepository.findJobSeekerByUserId(userId);
        var updatedJobSeeker = UserMapperUtil.updateJobSeeker(jobSeeker, jobSeekerRequestDTO, "");
        jobSeekerRepository.save(updatedJobSeeker);

        log.info("Updated JobSeeker with UserId: {}", updatedJobSeeker.getId());
        String resumeUrl = "Resume not uploaded";
        if (updatedJobSeeker.getResume() != null && !updatedJobSeeker.getResume().isEmpty()) {
            resumeUrl = fileStorageService.getFileUrl(updatedJobSeeker.getResume());
        }
        return UserMapperUtil.convertToJobSeekerResponse(updatedJobSeeker, resumeUrl);
    }

    @Override
    @Transactional
    public String deleteJobSeeker(UUID userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new UserNotFoundException(userId)
        );

        user.setActive(false);
        userRepository.save(user);
        log.info("JobSeeker Disabled jobseeker with UserId: {}", user.getId());
        return "JobSeeker has been disabled for UserId" + userId + " and deleted in 14 days";
    }

    @Override
    @Transactional
    public String uploadResume(MultipartFile file, Authentication authentication) {
        UUID userId = (UUID) authentication.getPrincipal();
        JobSeeker jobSeeker = jobSeekerRepository.findJobSeekerByUserId(userId);
        String userName = jobSeeker.getUser().getFirstName() + "_" + jobSeeker.getUser().getLastName();

        String fileName = "";
        try{
            fileName = fileStorageService.storeFile(file, userName);
        }catch (Exception e){
            log.error("Error uploading resume for JobSeeker with UserId: {}", jobSeeker.getUser().getId(), e);
            throw new RuntimeException("Failed to upload resume: " + e.getMessage());
        }

        jobSeeker.setResume(fileName);
        jobSeekerRepository.save(jobSeeker);
        log.info("Resume uploaded for JobSeeker with UserId: {}", jobSeeker.getUser().getId());
        return fileStorageService.getFileUrl(fileName);
    }

    @Override
    @Transactional
    public String deleteResume(Authentication authentication) {

        UUID userId = (UUID) authentication.getPrincipal();
        JobSeeker jobSeeker = jobSeekerRepository.findJobSeekerByUserId(userId);
        if (jobSeeker.getResume() == null && jobSeeker.getResume().isEmpty()) {
            log.warn("No resume found for JobSeeker with UserId: {}", jobSeeker.getUser().getId());
            return new FileNotFoundException("No resume found for JobSeeker with UserId: " + jobSeeker.getUser().getId()).getMessage();
        }
        String fileName = jobSeeker.getResume();

        try {
            fileStorageService.deleteFile(fileName);
        }catch (Exception e){
            log.error("Error deleting resume for JobSeeker with UserId: {}", jobSeeker.getUser().getId(), e);
            throw new RuntimeException("Failed to delete resume: " + e.getMessage());
        }
        jobSeeker.setResume(null);
        jobSeekerRepository.save(jobSeeker);
        log.info("Resume deleted for JobSeeker with UserId: {}", jobSeeker.getUser().getId());
        return "Resume deleted successfully";
    }
}
