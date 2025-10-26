package com.candidate.candidate_insight.config;

import com.candidate.candidate_insight.interceptor.ApiCallInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private ApiCallInterceptor apiCallInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(apiCallInterceptor);
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // allow all endpoints
                .allowedOrigins("http://localhost:3000") // allow React origin
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS");
    }
}
