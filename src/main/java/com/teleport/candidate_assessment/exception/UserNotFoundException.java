package com.teleport.candidate_assessment.exception;

public class UserNotFoundException extends TaskException {
    public UserNotFoundException(String userId) {
        super("User not found with ID: " + userId);
    }
}
