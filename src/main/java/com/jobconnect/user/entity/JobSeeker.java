package com.jobconnect.user.entity;

import com.jobconnect.auth.entity.User;
import com.jobconnect.job.enums.EmploymentType;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "job_seeker")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class JobSeeker {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id" , nullable = false)
    private User user;

    private String resumeUrl;

    private List<String> skillSets;

    private String degree;

    private EmploymentType  employmentType;
}
