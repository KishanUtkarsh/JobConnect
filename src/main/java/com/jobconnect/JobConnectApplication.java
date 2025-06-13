package com.jobconnect;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class JobConnectApplication {

	public static void main(String[] args) {
		SpringApplication.run(JobConnectApplication.class, args);
	}

}