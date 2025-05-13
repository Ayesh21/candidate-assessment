package com.teleport.candidate_assessment.service;

import com.teleport.candidate_assessment.dto.UserRequestDTO;
import com.teleport.candidate_assessment.dto.UserResponseDTO;
import reactor.core.publisher.Mono;

/** The interface User service. */
public interface UserService {
  /**
   * Create user response dto.
   *
   * @param userRequestDTO the user request dto
   */
  Mono<Void> create(final UserRequestDTO userRequestDTO);

  /**
   * Gets user by id.
   *
   * @param userId the user id
   * @return the user by id
   */
  Mono<UserResponseDTO> getUserById(final String userId);
}
