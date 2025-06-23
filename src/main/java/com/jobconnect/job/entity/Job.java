package com.jobconnect.job.entity;

import com.jobconnect.job.enums.EmploymentType;
import com.jobconnect.job.enums.JobStatus;
import com.jobconnect.user.entity.Recruiter;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Entity
@Table(name = "jobs")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Job {

    @Id()
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recruiter_id")
    private Recruiter recruiter;

    @Column(nullable = false, length = 30)
    private String title;

    @Column(nullable = false)
    private String role; // e.g., "Software Engineer", "Data Scientist"

    @ElementCollection
    @CollectionTable(
            name = "job_skills",
            joinColumns = @JoinColumn(name = "job_id", insertable = false, updatable = false)
    )
    @Column(name = "skill", nullable = false)
    private Set<String> skills = new HashSet<>();

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private String location;

    @Column(nullable = false)
    private String companyName;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private EmploymentType employmentType; // e.g., Full-time, Part-time, Contract

    @Column(nullable = false)
    private String salaryRange; // e.g., "50,000 - 70,000 USD"

    @Column(nullable = false)
    private String degree; // e.g., "Bachelor's degree in Computer Science or related field"

    private String benefits; // e.g., "Health insurance, 401(k), Paid time off"

    @Column(nullable = false)
    private LocalDate applicationDeadline; // e.g., "2023-12-31"

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private JobStatus status; // e.g., "Open", "Closed", "on Hold"

    @Column(updatable = false)
    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

}
