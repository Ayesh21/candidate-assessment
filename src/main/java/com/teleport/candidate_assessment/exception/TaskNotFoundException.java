package com.teleport.candidate_assessment.exception;

import static com.teleport.candidate_assessment.utils.ErrorConstant.TASK_NOT_FOUND;

/** The type Task not found exception. */
public class TaskNotFoundException extends TaskException {
  /**
   * Instantiates a new Task not found exception.
   *
   * @param userId the user id
   */
  public TaskNotFoundException(final String userId) {
    super(TASK_NOT_FOUND + userId);
  }
}
