package com.teleport.candidate_assessment.exception;

import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ServerWebExchange;

/** The type Global exception handler. */
@RestControllerAdvice
public class GlobalExceptionHandler {

  /**
   * Handle app exception error response.
   *
   * @param ex the ex
   * @param exchange the exchange
   * @return the error response
   */
@ExceptionHandler(TaskException.class)
  public ErrorResponse handleAppException(
      final TaskException ex, final ServerWebExchange exchange) {
    return ErrorResponse.of(
        "Application Error", ex.getMessage(), exchange.getRequest().getURI().getPath());
  }

  /**
   * Handle validation exception error response.
   *
   * @param ex the ex
   * @param exchange the exchange
   * @return the error response
   */
@ExceptionHandler(MethodArgumentNotValidException.class)
  public ErrorResponse handleValidationException(
      final MethodArgumentNotValidException ex, final ServerWebExchange exchange) {
    final StringBuilder message = new StringBuilder("Validation failed: ");
    ex.getBindingResult()
        .getFieldErrors()
        .forEach(
            err ->
                message.append(
                    String.format("[%s: %s] ", err.getField(), err.getDefaultMessage())));
    return ErrorResponse.of(
        "Validation Error", message.toString().trim(), exchange.getRequest().getURI().getPath());
  }

  /**
   * Handle illegal state error response.
   *
   * @param ex the ex
   * @param exchange the exchange
   * @return the error response
   */
@ExceptionHandler(IllegalStateException.class)
  public ErrorResponse handleIllegalState(
      final IllegalStateException ex, final ServerWebExchange exchange) {
    return ErrorResponse.of(
        "Illegal State", ex.getMessage(), exchange.getRequest().getURI().getPath());
  }

  /**
   * Handle generic exception error response.
   *
   * @param ex the ex
   * @param exchange the exchange
   * @return the error response
   */
  @ExceptionHandler(Exception.class)
  public ErrorResponse handleGenericException(
      final Exception ex, final ServerWebExchange exchange) {
    final String path = exchange != null ? exchange.getRequest().getURI().getPath() : "N/A";
    ex.printStackTrace();
    return ErrorResponse.of("Server Error", "Unexpected error occurred", path);
  }
}
