package com.teleport.candidate_assessment.service;

import com.teleport.candidate_assessment.dto.UserRequestDTO;
import com.teleport.candidate_assessment.dto.UserResponseDTO;

/** The interface User service. */
public interface UserService {
  /**
   * Create user response dto.
   *
   * @param userRequestDTO the user request dto
   * @return the user response dto
   */
  UserResponseDTO create(UserRequestDTO userRequestDTO);

  /**
   * Gets user by id.
   *
   * @param userId the user id
   * @return the user by id
   */
  UserResponseDTO getUserById(String userId);
}
