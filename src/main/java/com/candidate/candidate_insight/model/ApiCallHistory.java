package com.candidate.candidate_insight.model;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class ApiCallHistory {
    private Long id;
    private LocalDateTime timestamp;
    private String endpoint;
    private String httpMethod;
    private String userIdentifier;
    private int responseStatus;
}
