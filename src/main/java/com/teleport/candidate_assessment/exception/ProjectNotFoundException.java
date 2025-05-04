package com.teleport.candidate_assessment.exception;

/** The type Project not found exception. */
public class ProjectNotFoundException extends TaskException {
  /**
   * Instantiates a new Project not found exception.
   *
   * @param projectId the project id
   */
  public ProjectNotFoundException(String projectId) {
    super("Project not found with ID: " + projectId);
  }
}
