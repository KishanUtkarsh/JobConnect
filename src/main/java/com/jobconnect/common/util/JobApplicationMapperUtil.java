package com.jobconnect.common.util;

import com.jobconnect.application.dto.JobApplicationResponseDTO;
import com.jobconnect.application.dto.JobApplyResponseDTO;
import com.jobconnect.application.entity.JobApplication;
import com.jobconnect.job.entity.Job;
import com.jobconnect.application.enums.ApplicationStatus;
import com.jobconnect.user.entity.JobSeeker;

import java.time.LocalDate;
import java.util.UUID;

public class JobApplicationMapperUtil {
    private  JobApplicationMapperUtil() {
        throw new IllegalStateException("Utility class not meant to be instantiated");
    }

    public static JobApplication convertToJobApplicationEntity(
            String resumeLink,Job job, JobSeeker jobSeeker, ApplicationStatus status
            ) {
        JobApplication jobApplication = new JobApplication();
        jobApplication.setJobSeeker(jobSeeker);
        jobApplication.setJob(job);
        jobApplication.setStatus(status);
        jobApplication.setAppliedAt(LocalDate.now());
        jobApplication.setResumeLink(resumeLink);
        jobApplication.setSkillSets(jobSeeker.getSkillSets());
        jobApplication.setDegree(jobSeeker.getDegree());
        return jobApplication;
    }

    public static JobApplicationResponseDTO convertToJobApplicationResponseDTO(
            JobApplication jobApplication, Job job, JobSeeker jobSeeker
    ) {
        return new JobApplicationResponseDTO(
                jobApplication.getId().toString(),
                job.getId().toString(),
                job.getTitle(),
                job.getDescription(),
                job.getLocation(),
                job.getRecruiter().getCompanyName(),
                jobSeeker.getUser().getId().toString(),
                jobApplication.getStatus().toString(),
                jobApplication.getAppliedAt().toString(),
                jobApplication.getResumeLink(),
                jobSeeker.getDegree(),
                job.getEmploymentType().toString()
        );
    }

    public static JobApplyResponseDTO convertToJobApplyResponseDTO(JobApplication application, Job job
    ) {
        return new JobApplyResponseDTO(
                application.getId(),
                job.getId(),
                job.getTitle(),
                application.getStatus(),
                application.getAppliedAt()

        );
    }
}
