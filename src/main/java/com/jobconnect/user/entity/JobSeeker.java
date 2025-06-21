package com.jobconnect.user.entity;

import com.jobconnect.auth.entity.User;
import com.jobconnect.job.enums.EmploymentType;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;
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

    private String resume;

    @ElementCollection
    @CollectionTable(
            name = "job_seeker_skills",
            joinColumns = @JoinColumn(name = "job_seeker_id", insertable = false, updatable = false)
    )
    @Column(name = "skill")
    private Set<String> skills = new HashSet<>();

    private String degree;

    private EmploymentType  employmentType;
}
