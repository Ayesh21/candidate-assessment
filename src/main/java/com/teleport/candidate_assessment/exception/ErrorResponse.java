package com.teleport.candidate_assessment.exception;

import java.time.LocalDateTime;

public record ErrorResponse(
        String error,
        String message,
        String path,
        LocalDateTime timestamp
) {
    public static ErrorResponse of(String error, String message, String path) {
        return new ErrorResponse(error, message, path, LocalDateTime.now());
    }
}
