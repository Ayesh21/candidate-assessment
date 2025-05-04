package com.teleport.candidate_assessment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class CandidateAssessmentApplication {

	public static void main(String[] args) {
		SpringApplication.run(CandidateAssessmentApplication.class, args);
	}

}
