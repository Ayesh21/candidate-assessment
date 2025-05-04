package com.teleport.candidate_assessment.interceptor;

import io.micrometer.tracing.Span;
import io.micrometer.tracing.Tracer;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

/** The type Tracing interceptor. */
@Component
public class TracingInterceptor implements HandlerInterceptor {
  private final Tracer tracer;

  /**
   * Instantiates a new Tracing interceptor.
   *
   * @param tracer the tracer
   */
  public TracingInterceptor(Tracer tracer) {
    this.tracer = tracer;
  }

  @Override
  public boolean preHandle(
      HttpServletRequest request, HttpServletResponse response, Object handler) {
    String spanName = request.getMethod() + " " + request.getRequestURI();
    Span span = this.tracer.nextSpan().name(spanName).start();

    // Store the span and scope
    request.setAttribute("customSpan", span);
    request.setAttribute("customScope", this.tracer.withSpan(span));

    return true;
  }

  @Override
  public void afterCompletion(
      HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
    Span span = (Span) request.getAttribute("customSpan");
    Tracer.SpanInScope scope = (Tracer.SpanInScope) request.getAttribute("customScope");

    if (scope != null) {
      scope.close();
    }

    if (span != null) {
      if (ex != null) {
        span.error(ex);
      }
      span.end();
    }
  }
}
