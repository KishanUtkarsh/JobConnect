package com.jobconnect.application.entity;

import com.jobconnect.application.enums.ApplicationStatus;
import com.jobconnect.job.entity.Job;
import com.jobconnect.user.entity.JobSeeker;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "job_application")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class JobApplication {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "job_id", nullable = false)
    private Job job;

    @ManyToOne
    @JoinColumn(name = "job_seeker_id")
    private JobSeeker jobSeeker;

    @NotNull
    @Enumerated(EnumType.STRING)
    private ApplicationStatus status;

    @NotNull
    @Column(updatable = false)
    @CreatedDate
    private LocalDate appliedAt;

    @NotNull
    @Column(updatable = false)
    private String resumeLink;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(
            name = "job_applicant_skills",
            joinColumns = @JoinColumn(name = "application_id")
    )
    @Column(name = "skill", nullable = false)
    private Set<String> skills = new HashSet<>();

    @Column(updatable = false)
    private String degree;


}
