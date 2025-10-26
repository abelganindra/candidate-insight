package com.candidate.candidate_insight.interceptor;

import com.candidate.candidate_insight.model.ApiCallHistory;
import com.candidate.candidate_insight.repository.ApiCallHistoryRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.time.LocalDateTime;

@Component
public class ApiCallInterceptor implements HandlerInterceptor {

    private final ApiCallHistoryRepository historyRepository;

    public ApiCallInterceptor(ApiCallHistoryRepository historyRepository) {
        this.historyRepository = historyRepository;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        ApiCallHistory history = new ApiCallHistory();
        history.setTimestamp(LocalDateTime.now());
        history.setEndpoint(request.getRequestURI());
        history.setHttpMethod(request.getMethod());
        history.setUserIdentifier("guest"); // replace with actual user if available
        history.setResponseStatus(response.getStatus());

        historyRepository.save(history);
    }
}
