package com.teleport.candidate_assessment.exception;

public class ProjectNotFoundException extends TaskException {
    public ProjectNotFoundException(String projectId) {
        super("Project not found with ID: " + projectId);
    }
}