package com.jobconnect.repository;

import com.jobconnect.user.entity.JobSeeker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface JobSeekerRepository extends JpaRepository<JobSeeker, UUID> {

    JobSeeker findJobSeekerByUser(UUID userId);
}
