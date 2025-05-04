package com.teleport.candidate_assessment.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.teleport.candidate_assessment.dto.UserRequestDTO;
import com.teleport.candidate_assessment.dto.UserResponseDTO;
import com.teleport.candidate_assessment.entity.User;
import com.teleport.candidate_assessment.repository.UserRepository;
import com.teleport.candidate_assessment.service.impl.UserServiceImpl;
import java.util.Optional;

import com.teleport.candidate_assessment.transformer.UserTransformer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

/** The type User service test. */
@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
  @Mock private UserRepository userRepository;
  @Mock
  private UserTransformer userTransformer;
  @InjectMocks private UserServiceImpl userService;
  private UserRequestDTO userRequestDTO;
  private User user;

  /** Sets up. */
  @BeforeEach
  void setUp() {
    userRequestDTO = new UserRequestDTO("JohnDoe", "john@example.com");
    user = new User(1L,"user123", "JohnDoe", "john@example.com");
  }

  /** Create user successfully test. */
  @Test
  void createUserSuccessfullyTest() {
    when(userTransformer.toEntity(userRequestDTO)).thenReturn(user);
    when(userRepository.save(any(User.class))).thenReturn(user);

    userService.create(userRequestDTO);

    verify(userTransformer, times(1)).toEntity(userRequestDTO);
    verify(userRepository, times(1)).save(user);
  }

  /** Return user when id exists test. */
  @Test
  void returnUserWhenIdExistsTest() {
    when(userRepository.findById("user123")).thenReturn(Optional.of(user));

    UserResponseDTO result = userService.getUserById("user123");

    assertNotNull(result);
    assertEquals("user123", result.id());
    assertEquals("JohnDoe", result.username());
    assertEquals("john@example.com", result.email());
  }
}
