package com.teleport.candidate_assessment.config;

import com.teleport.candidate_assessment.interceptor.TracingInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

public class LoggerConfig implements WebMvcConfigurer {
    private final TracingInterceptor tracingInterceptor;

    public LoggerConfig(TracingInterceptor tracingInterceptor) {
        this.tracingInterceptor = tracingInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(tracingInterceptor);
    }
}

