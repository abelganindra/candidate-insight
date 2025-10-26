package com.candidate.candidate_insight;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication(scanBasePackages = {"com.candidate.candidate_insight", "main.java.com.candidate.candidate_insight"})
public class CandidateInsightApplication {

	public static void main(String[] args) {
		SpringApplication.run(CandidateInsightApplication.class, args);
	}

}
