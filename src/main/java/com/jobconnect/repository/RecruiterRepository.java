package com.jobconnect.repository;

import com.jobconnect.auth.entity.User;
import com.jobconnect.user.entity.Recruiter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface RecruiterRepository extends JpaRepository<Recruiter, UUID> {

    Recruiter findRecruiterByUserId(UUID userId);

    List<Recruiter> user(User user);
}
