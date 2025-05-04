package com.teleport.candidate_assessment.exception;

/** The type User not found exception. */
public class UserNotFoundException extends TaskException {
  /**
   * Instantiates a new User not found exception.
   *
   * @param userId the user id
   */
  public UserNotFoundException(String userId) {
    super("User not found with ID: " + userId);
  }
}
