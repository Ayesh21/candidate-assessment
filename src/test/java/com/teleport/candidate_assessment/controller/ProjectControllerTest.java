package com.teleport.candidate_assessment.controller;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.doReturn;

import com.teleport.candidate_assessment.dto.ProjectRequestDTO;
import com.teleport.candidate_assessment.dto.ProjectResponseDTO;
import com.teleport.candidate_assessment.service.ProjectService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

/** The type Project controller test. */
@ExtendWith(MockitoExtension.class)
class ProjectControllerTest {

  @Mock
  private ProjectService projectService;

  @InjectMocks
  private ProjectController projectController;

  private WebTestClient webTestClient;

  private ProjectRequestDTO sampleRequest;
  private ProjectResponseDTO sampleResponse;

  /** Sets up. */
  @BeforeEach
  void setUp() {
    webTestClient = WebTestClient.bindToController(projectController).build();

    sampleRequest = new ProjectRequestDTO("Project Alpha", "user123");
    sampleResponse = new ProjectResponseDTO("proj1", "Project Alpha", "user123");
  }

  /**
   * Should create project successfully.
   *
   * @throws Exception the exception
   */
  @Test
  void shouldCreateProjectSuccessfully() throws Exception {
    doReturn(Mono.just(sampleResponse)).when(projectService).create(sampleRequest);

    webTestClient
        .post()
        .uri("/api/projects")
        .contentType(MediaType.APPLICATION_JSON)
        .body(Mono.just(sampleRequest), ProjectRequestDTO.class)
        .exchange()
        .expectStatus()
        .isOk()
        .expectBody()
        .jsonPath("$.id")
        .isEqualTo("proj1")
        .jsonPath("$.name")
        .isEqualTo("Project Alpha")
        .jsonPath("$.ownerId")
        .isEqualTo("user123");

    verify(projectService, times(1)).create(sampleRequest);
  }

  /**
   * Should fetch project by id successfully.
   *
   * @throws Exception the exception
   */
  @Test
  void shouldFetchProjectByIdSuccessfully() throws Exception {
    doReturn(Mono.just(sampleResponse)).when(projectService).getProjectById("proj1");

    webTestClient
        .get()
        .uri("/api/projects/proj1")
        .exchange()
        .expectStatus()
        .isOk()
        .expectBody()
        .jsonPath("$.id")
        .isEqualTo("proj1")
        .jsonPath("$.name")
        .isEqualTo("Project Alpha")
        .jsonPath("$.ownerId")
        .isEqualTo("user123");

    verify(projectService, times(1)).getProjectById("proj1");
  }
}
