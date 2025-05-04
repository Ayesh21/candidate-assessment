package com.teleport.candidate_assessment.config;

import com.teleport.candidate_assessment.interceptor.TracingInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/** The type Logger config. */
public class LoggerConfig implements WebMvcConfigurer {
  private final TracingInterceptor tracingInterceptor;

  /**
   * Instantiates a new Logger config.
   *
   * @param tracingInterceptor the tracing interceptor
   */
  public LoggerConfig(final TracingInterceptor tracingInterceptor) {
    this.tracingInterceptor = tracingInterceptor;
  }

  @Override
  public void addInterceptors(final InterceptorRegistry registry) {
    registry.addInterceptor(tracingInterceptor);
  }
}
