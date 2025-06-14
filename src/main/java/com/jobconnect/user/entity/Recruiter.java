package com.jobconnect.user.entity;

import com.jobconnect.auth.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "recruiter")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Recruiter {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;

    private String companyName;

    private String position;

    private String companyUrl;
}
