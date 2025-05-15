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
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.UUID;

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
  @Override
  public Mono<Void> create(final UserRequestDTO userRequestDTO) {
    logger.info("Saving user {}", userRequestDTO);
    final User user = userTransformer.toEntity(userRequestDTO);
    user.setId(UUID.randomUUID().toString());
    return userRepository.save(user).then();
  }

  /**
   * Gets user by id.
   *
   * @param userId the user id
   * @return the user by id
   */
  @Override
  public Mono<UserResponseDTO> getUserById(final String userId) {
    return userRepository.findById(userId)
            .switchIfEmpty(Mono.error(new UserNotFoundException(userId)))
            .map(UserResponseDTO::fromEntity);
  }
}
