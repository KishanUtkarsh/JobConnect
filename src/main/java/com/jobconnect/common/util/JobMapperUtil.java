package com.jobconnect.common.util;

import com.jobconnect.job.dto.JobDto;
import com.jobconnect.job.entity.Job;

import java.util.Collections;

public class JobMapperUtil {

    private JobMapperUtil() {
        throw new IllegalStateException("Utility class, cannot be instantiated");
    }


    public static Job jobDtoToJobEntity(JobDto dto) {
        Job job = new Job();
        job.setTitle(dto.title());
        job.setRole(dto.role());
        job.setSkillSet(Collections.singletonList(String.valueOf(dto.skillSet())));
        job.setDescription(dto.description());
        job.setLocation(dto.location());
        job.setCompanyName(dto.companyName());
        job.setEmploymentType(dto.employmentType());
        job.setSalaryRange(dto.salaryRange());
        job.setRequirements(dto.requirements());
        job.setBenefits(dto.benefits());
        job.setApplicationDeadline(dto.applicationDeadline());
        job.setStatus(dto.status());
        return job;
    }

    public static JobDto jobEntityToJobDto(Job job) {
        return new JobDto(
                job.getId(),
                job.getCreatedAt(),
                job.getUpdatedAt(),
                job.getTitle(),
                job.getRole(),
                job.getSkillSet(),
                job.getDescription(),
                job.getLocation(),
                job.getCompanyName(),
                job.getEmploymentType(),
                job.getSalaryRange(),
                job.getRequirements(),
                job.getBenefits(),
                job.getApplicationDeadline(),
                job.getStatus()
        );
    }

    public static Job updateJobEntityFromDto(Job existingJob, JobDto dto) {
        existingJob.setTitle(dto.title());
        existingJob.setRole(dto.role());
        existingJob.setSkillSet(Collections.singletonList(String.valueOf(dto.skillSet())));
        existingJob.setDescription(dto.description());
        existingJob.setLocation(dto.location());
        existingJob.setCompanyName(dto.companyName());
        existingJob.setEmploymentType(dto.employmentType());
        existingJob.setSalaryRange(dto.salaryRange());
        existingJob.setRequirements(dto.requirements());
        existingJob.setBenefits(dto.benefits());
        existingJob.setApplicationDeadline(dto.applicationDeadline());
        existingJob.setStatus(dto.status());
        return existingJob;
    }
}
