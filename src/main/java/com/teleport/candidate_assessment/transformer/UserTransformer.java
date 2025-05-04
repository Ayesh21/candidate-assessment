package com.teleport.candidate_assessment.transformer;

import com.teleport.candidate_assessment.dto.UserRequestDTO;
import com.teleport.candidate_assessment.dto.UserResponseDTO;
import com.teleport.candidate_assessment.entity.User;

/** The type User transformer. */
public class UserTransformer {

  /**
   * To entity user.
   *
   * @param dto the dto
   * @return the user
   */
  public static User toEntity(UserRequestDTO dto) {
    User user = new User();
    user.setName(dto.username());
    user.setEmail(dto.email());
    return user;
  }

  /**
   * To response user response dto.
   *
   * @param user the user
   * @return the user response dto
   */
  public static UserResponseDTO toResponse(User user) {
    return new UserResponseDTO(user.getId(), user.getName(), user.getEmail());
  }
}
