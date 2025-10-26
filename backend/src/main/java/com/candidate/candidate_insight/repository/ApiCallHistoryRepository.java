package com.candidate.candidate_insight.repository;

import com.candidate.candidate_insight.model.ApiCallHistory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class ApiCallHistoryRepository {

    private final JdbcTemplate jdbcTemplate;

    public ApiCallHistoryRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void save(ApiCallHistory history) {
        String sql = "INSERT INTO api_call_history (timestamp, endpoint, http_method, user_identifier, response_status) " +
                     "VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql,
                history.getTimestamp(),
                history.getEndpoint(),
                history.getHttpMethod(),
                history.getUserIdentifier(),
                history.getResponseStatus()
        );
    }
}
