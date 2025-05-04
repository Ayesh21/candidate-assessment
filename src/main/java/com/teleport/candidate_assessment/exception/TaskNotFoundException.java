package com.teleport.candidate_assessment.exception;

public class TaskNotFoundException extends TaskException {
    public TaskNotFoundException(String userId) {
        super("Task not found with ID: " + userId);
    }
}
