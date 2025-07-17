package com.jobconnect.common.util;

import com.jobconnect.job.dto.JobRequestDto;
import com.jobconnect.job.dto.JobResponseDto;
import com.jobconnect.job.entity.Job;
import com.jobconnect.user.entity.Recruiter;

public class JobMapperUtil {

    private JobMapperUtil() {
        throw new IllegalStateException("Utility class, cannot be instantiated");
    }


    public static Job jobDtoToJobEntity(JobRequestDto dto, Recruiter recruiter) {
        Job job = new Job();
        job.setRecruiter(recruiter);
        job.setTitle(dto.title());
        job.setRole(dto.role());
        job.setSkills(dto.skills());
        job.setDescription(dto.description());
        job.setLocation(dto.location());
        job.setCompanyName(dto.companyName());
        job.setEmploymentType(dto.employmentType());
        job.setSalaryRange(dto.salaryRange());
        job.setDegree(dto.degree());
        job.setBenefits(dto.benefits());
        job.setApplicationDeadline(dto.applicationDeadline());
        job.setStatus(dto.status());
        return job;
    }

    public static JobResponseDto jobEntityToJobDto(Job job) {
        return new JobResponseDto(
                job.getId(),
                job.getCreatedAt(),
                job.getUpdatedAt(),
                job.getRecruiter(),
                job.getTitle(),
                job.getRole(),
                job.getSkills(),
                job.getDescription(),
                job.getLocation(),
                job.getCompanyName(),
                job.getEmploymentType(),
                job.getSalaryRange(),
                job.getDegree(),
                job.getBenefits(),
                job.getApplicationDeadline(),
                job.getStatus()
        );
    }

    public static Job updateJobEntityFromDto(Job existingJob, JobRequestDto dto) {
        existingJob.setTitle(dto.title());
        existingJob.setRole(dto.role());
        existingJob.setSkills(dto.skills());
        existingJob.setDescription(dto.description());
        existingJob.setLocation(dto.location());
        existingJob.setCompanyName(dto.companyName());
        existingJob.setEmploymentType(dto.employmentType());
        existingJob.setSalaryRange(dto.salaryRange());
        existingJob.setDegree(dto.degree());
        existingJob.setBenefits(dto.benefits());
        existingJob.setApplicationDeadline(dto.applicationDeadline());
        existingJob.setStatus(dto.status());
        return existingJob;
    }
}
