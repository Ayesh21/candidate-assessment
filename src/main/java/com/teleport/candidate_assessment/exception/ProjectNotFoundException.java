package com.teleport.candidate_assessment.exception;

import static com.teleport.candidate_assessment.utils.ErrorConstant.PROJECT_NOT_FOUND;

/** The type Project not found exception. */
public class ProjectNotFoundException extends TaskException {
  /**
   * Instantiates a new Project not found exception.
   *
   * @param projectId the project id
   */
  public ProjectNotFoundException(String projectId) {
    super(PROJECT_NOT_FOUND + projectId);
  }
}
