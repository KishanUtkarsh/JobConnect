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

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "job_application")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
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
    private LocalDate appliedAt;

    @NotNull
    @Column(updatable = false)
    private String resumeLink;

    @Column(updatable = false)
    private List<String> skillSets;

    @Column(updatable = false)
    private String degree;


}
