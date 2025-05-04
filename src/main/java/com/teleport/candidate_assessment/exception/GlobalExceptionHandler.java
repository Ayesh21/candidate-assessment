package com.teleport.candidate_assessment.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/** The type Global exception handler. */
@RestControllerAdvice
public class GlobalExceptionHandler {

  /**
   * Handle app exception error response.
   *
   * @param ex the ex
   * @param request the request
   * @return the error response
   */
  @ExceptionHandler(TaskException.class)
  public ErrorResponse handleAppException(TaskException ex, HttpServletRequest request) {
    return ErrorResponse.of("Application Error", ex.getMessage(), request.getRequestURI());
  }

  /**
   * Handle validation exception error response.
   *
   * @param ex the ex
   * @param request the request
   * @return the error response
   */
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ErrorResponse handleValidationException(
      MethodArgumentNotValidException ex, HttpServletRequest request) {
    StringBuilder message = new StringBuilder("Validation failed: ");
    ex.getBindingResult()
        .getFieldErrors()
        .forEach(
            err ->
                message.append(
                    String.format("[%s: %s] ", err.getField(), err.getDefaultMessage())));
    return ErrorResponse.of("Validation Error", message.toString().trim(), request.getRequestURI());
  }

  /**
   * Handle illegal state error response.
   *
   * @param ex the ex
   * @param request the request
   * @return the error response
   */
  @ExceptionHandler(IllegalStateException.class)
  public ErrorResponse handleIllegalState(IllegalStateException ex, HttpServletRequest request) {
    return ErrorResponse.of("Illegal State", ex.getMessage(), request.getRequestURI());
  }

  /**
   * Handle generic exception error response.
   *
   * @param ex the ex
   * @param request the request
   * @return the error response
   */
  @ExceptionHandler(Exception.class)
  public ErrorResponse handleGenericException(Exception ex, HttpServletRequest request) {
    String path = request != null ? request.getRequestURI() : "N/A";
    ex.printStackTrace();
    return ErrorResponse.of("Server Error", "Unexpected error occurred", path);
  }
}
