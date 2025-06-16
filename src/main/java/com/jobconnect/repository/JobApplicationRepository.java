package com.jobconnect.repository;

import com.jobconnect.application.entity.JobApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface JobApplicationRepository extends JpaRepository<JobApplication, UUID> {

    @Query(
        "SELECT ja FROM JobApplication ja " +
        "WHERE ja.job.id = :jobId AND ja.jobSeeker.id = :jobSeekerId"
    )
    JobApplication findJobApplicationByJobIdAndJobSeekerId(@Param("jobId") UUID jobId,@Param("jobSeekerId") UUID jobSeekerId);

    List<JobApplication> findJobApplicationByJobId(UUID jobId);

    List<JobApplication> findJobApplicationByJobSeekerId(UUID id);
}
