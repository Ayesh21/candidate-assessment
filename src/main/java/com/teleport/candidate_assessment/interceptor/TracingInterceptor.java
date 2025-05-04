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
  public TracingInterceptor(final Tracer tracer) {
    this.tracer = tracer;
  }

  @Override
  public boolean preHandle(
      final HttpServletRequest request, final HttpServletResponse response, final Object handler) {
    final String spanName = request.getMethod() + " " + request.getRequestURI();
    final Span span = this.tracer.nextSpan().name(spanName).start();

    // Store the span and scope
    request.setAttribute("customSpan", span);
    request.setAttribute("customScope", this.tracer.withSpan(span));

    return true;
  }

  @Override
  public void afterCompletion(
      final HttpServletRequest request,
      final HttpServletResponse response,
      final Object handler,
      final Exception ex) {
    final Span span = (Span) request.getAttribute("customSpan");
    final Tracer.SpanInScope scope = (Tracer.SpanInScope) request.getAttribute("customScope");

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
