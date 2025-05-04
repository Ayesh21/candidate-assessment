package com.teleport.candidate_assessment.transformer;

import com.teleport.candidate_assessment.dto.UserRequestDTO;
import com.teleport.candidate_assessment.dto.UserResponseDTO;
import com.teleport.candidate_assessment.entity.User;
import org.springframework.stereotype.Component;

/** The type User transformer. */
@Component
public class UserTransformer {

  /**
   * To entity user.
   *
   * @param dto the dto
   * @return the user
   */
  public User toEntity(UserRequestDTO dto) {
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
  public UserResponseDTO toResponse(User user) {
    return new UserResponseDTO(user.getId(), user.getName(), user.getEmail());
  }
}
