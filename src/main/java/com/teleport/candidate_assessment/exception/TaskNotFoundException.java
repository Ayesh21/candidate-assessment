package com.teleport.candidate_assessment.exception;

/** The type Task not found exception. */
public class TaskNotFoundException extends TaskException {
  /**
   * Instantiates a new Task not found exception.
   *
   * @param userId the user id
   */
  public TaskNotFoundException(String userId) {
    super("Task not found with ID: " + userId);
  }
}
