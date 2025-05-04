package com.teleport.candidate_assessment.exception;

import static com.teleport.candidate_assessment.utils.ErrorConstant.USER_NOT_FOUND;

/** The type User not found exception. */
public class UserNotFoundException extends TaskException {
  /**
   * Instantiates a new User not found exception.
   *
   * @param userId the user id
   */
  public UserNotFoundException(final String userId) {
    super(USER_NOT_FOUND + userId);
  }
}
