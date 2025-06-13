package com.jobconnect.job.entity;

import com.jobconnect.job.enums.EmploymentType;
import com.jobconnect.job.enums.JobStatus;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "jobs")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Job {

    @Id()
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, length = 30)
    private String title;

    @Column(nullable = false)
    private String role; // e.g., "Software Engineer", "Data Scientist"

    @Column(nullable = false)
    private List<String> skillSet; // e.g., "Java, Spring Boot, REST APIs"

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
    private String requirements; // e.g., "Bachelor's degree in Computer Science or related field"

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
