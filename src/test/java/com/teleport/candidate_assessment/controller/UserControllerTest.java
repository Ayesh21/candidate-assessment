package com.teleport.candidate_assessment.controller;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

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
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

/** The type User controller test. */
@ExtendWith(MockitoExtension.class)
class UserControllerTest {

  @Mock
  private UserService userService;

  @InjectMocks
  private UserController userController;

  private WebTestClient webTestClient;

  private UserResponseDTO sampleResponse;

  /** Sets up. */
  @BeforeEach
  void setUp() {
    webTestClient = WebTestClient.bindToController(userController).build();
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

    doReturn(Mono.just("OK")).when(userService).create(requestDTO);

    webTestClient
        .post()
        .uri("/api/users") // adjust path if needed
        .contentType(MediaType.APPLICATION_JSON)
        .body(Mono.just(requestDTO), UserRequestDTO.class)
        .exchange()
        .expectStatus()
        .isOk()
        .expectBody(String.class)
        .isEqualTo("OK");

    verify(userService, times(1)).create(requestDTO);
  }

  /**
   * Should fetch user by id successfully.
   *
   * @throws Exception the exception
   */
  @Test
  void shouldFetchUserByIdSuccessfully() throws Exception {
    doReturn(Mono.just(sampleResponse)).when(userService).getUserById("1");

    webTestClient
        .get()
        .uri("/api/users/1")
        .exchange()
        .expectStatus()
        .isOk()
        .expectBody()
        .jsonPath("$.id")
        .isEqualTo("1")
        .jsonPath("$.username")
        .isEqualTo("johndoe")
        .jsonPath("$.email")
        .isEqualTo("johndoe@example.com");
  }
}
