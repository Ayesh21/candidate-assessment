package com.teleport.candidate_assessment.service.impl;

import com.teleport.candidate_assessment.dto.UserRequestDTO;
import com.teleport.candidate_assessment.dto.UserResponseDTO;
import com.teleport.candidate_assessment.entity.User;
import com.teleport.candidate_assessment.exception.UserNotFoundException;
import com.teleport.candidate_assessment.repository.UserRepository;
import com.teleport.candidate_assessment.service.UserService;
import com.teleport.candidate_assessment.transformer.UserTransformer;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
  private final UserRepository userRepository;

  /**
   * Create user response dto.
   *
   * @param userRequestDTO the user request dto
   * @return the user response dto
   */
  @Override
  public UserResponseDTO create(UserRequestDTO userRequestDTO) {
    User user = UserTransformer.toEntity(userRequestDTO);
    return UserTransformer.toResponse(userRepository.save(user));
  }

  /**
   * Gets user by id.
   *
   * @param userId the user id
   * @return the user by id
   */
  @Override
  @Cacheable(value = "userData", key = "#userId")
  public UserResponseDTO getUserById(String userId) {
//    try {
//      Thread.sleep(5000); // Simulates a delay
//    } catch (InterruptedException e) {
//      e.printStackTrace();
//    }
    return UserResponseDTO.fromEntity(
            userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId)));
  }
}
