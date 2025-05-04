package com.teleport.candidate_assessment.dto;

import com.teleport.candidate_assessment.entity.User;

/** The type User response dto. */
public record UserResponseDTO(String id, String username, String email) {

  public static UserResponseDTO fromEntity(User user) {
    return new UserResponseDTO(user.getId(), user.getName(), user.getEmail());
  }
}
