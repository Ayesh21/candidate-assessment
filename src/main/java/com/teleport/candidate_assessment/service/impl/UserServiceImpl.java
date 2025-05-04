package com.teleport.candidate_assessment.service.impl;

import com.teleport.candidate_assessment.dto.UserRequestDTO;
import com.teleport.candidate_assessment.dto.UserResponseDTO;
import com.teleport.candidate_assessment.entity.User;
import com.teleport.candidate_assessment.exception.UserNotFoundException;
import com.teleport.candidate_assessment.repository.UserRepository;
import com.teleport.candidate_assessment.service.UserService;
import com.teleport.candidate_assessment.transformer.UserTransformer;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
  private final UserRepository userRepository;
  private final UserTransformer userTransformer;

  private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

  /**
   * Create user response dto.
   *
   * @param userRequestDTO the user request dto
   */
  @Transactional
  @Override
  public void create(final UserRequestDTO userRequestDTO) {
    logger.info("asynchronously saving user ", userRequestDTO);
    final User user = userTransformer.toEntity(userRequestDTO);
    userRepository.save(user);
  }

  /**
   * Gets user by id.
   *
   * @param userId the user id
   * @return the user by id
   */
  @Override
  @Cacheable(value = "userData", key = "#userId")
  public UserResponseDTO getUserById(final String userId) {
    return UserResponseDTO.fromEntity(
        userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId)));
  }
}
