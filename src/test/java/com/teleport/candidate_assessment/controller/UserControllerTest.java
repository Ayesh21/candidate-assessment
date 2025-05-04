package com.teleport.candidate_assessment.controller;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.teleport.candidate_assessment.dto.UserRequestDTO;
import com.teleport.candidate_assessment.dto.UserResponseDTO;
import com.teleport.candidate_assessment.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

/** The type User controller test. */
@ExtendWith(MockitoExtension.class)
class UserControllerTest {

  @Mock private UserService userService;

  @InjectMocks private UserController userController;

  private MockMvc mockMvc;
  private final ObjectMapper objectMapper = new ObjectMapper();

  private UserResponseDTO sampleResponse;

  /** Sets up. */
  @BeforeEach
  void setUp() {
    mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    sampleResponse = new UserResponseDTO("1", "johndoe", "johndoe@example.com");
  }

  /**
   * Should create user successfully.
   *
   * @throws Exception the exception
   */
  @Test
  void shouldCreateUserSuccessfully() throws Exception {
    UserRequestDTO requestDTO = new UserRequestDTO("testuser", "test@example.com");

    mockMvc.perform(post("/api/users") // adjust path if needed
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(requestDTO)))
            .andExpect(status().isOk());

    verify(userService, times(1)).create(requestDTO);
  }

  /**
   * Should fetch user by id successfully.
   *
   * @throws Exception the exception
   */
  @Test
  void shouldFetchUserByIdSuccessfully() throws Exception {
    when(userService.getUserById("1")).thenReturn(sampleResponse);

    mockMvc
        .perform(get("/api/users/1"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value("1"))
        .andExpect(jsonPath("$.username").value("johndoe"))
        .andExpect(jsonPath("$.email").value("johndoe@example.com"));
  }
}
