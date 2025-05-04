package com.teleport.candidate_assessment.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(TaskException.class)
    public ErrorResponse handleAppException(TaskException ex, HttpServletRequest request) {
        return ErrorResponse.of("Application Error", ex.getMessage(), request.getRequestURI());
    }

//    @ExceptionHandler(MethodArgumentNotValidException.class)
//    public ResponseEntity<Map<String, String>> handleValidationException(MethodArgumentNotValidException ex) {
//        Map<String, String> errors = new HashMap<>();
//        ex.getBindingResult().getFieldErrors().forEach(err ->
//                errors.put(err.getField(), err.getDefaultMessage()));
//        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
//    }

    @ExceptionHandler(IllegalStateException.class)
    public ErrorResponse handleIllegalState(IllegalStateException ex, HttpServletRequest request) {
        return ErrorResponse.of("Illegal State", ex.getMessage(), request.getRequestURI());
    }

    @ExceptionHandler(Exception.class)
    public ErrorResponse handleGenericException(Exception ex, HttpServletRequest request) {
        String path = request != null ? request.getRequestURI() : "N/A";
        ex.printStackTrace();
        return ErrorResponse.of("Server Error", "Unexpected error occurred", path);
    }

}
