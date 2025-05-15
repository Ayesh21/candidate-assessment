package com.teleport.candidate_assessment.controller;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.teleport.candidate_assessment.dto.TaskRequestDTO;
import com.teleport.candidate_assessment.dto.TaskResponseDTO;
import com.teleport.candidate_assessment.service.TaskService;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/** The type Task controller test. */
@ExtendWith(MockitoExtension.class)
class TaskControllerTest {

  @Mock
  private TaskService taskService;

  @InjectMocks
  private TaskController taskController;

  private WebTestClient webTestClient;
  private TaskResponseDTO sampleTask;
  private Pageable pageable;

  /** Sets up. */
  @BeforeEach
  void setUp() {
    webTestClient = WebTestClient.bindToController(taskController).build();

    sampleTask =
        new TaskResponseDTO(
            "task123",
            "Task Testing",
            "IN_PROGRESS",
            "HIGH",
            "user123",
            "proj123",
            LocalDateTime.of(2025, 5, 4, 10, 0));

    pageable = PageRequest.of(0, 10, Sort.by("dueDate").descending());
  }

  /** Should create task. @throws Exception the exception @throws Exception the exception */
  @Test
  void shouldCreateTask() throws Exception {
    TaskRequestDTO requestDTO =
        new TaskRequestDTO(
            "Task Testing",
            "IN_PROGRESS",
            "HIGH",
            "user123",
            "proj123",
            LocalDateTime.now().plusDays(1));
    doReturn(Mono.just(sampleTask)).when(taskService).createTask(requestDTO);

    webTestClient
        .post()
        .uri("/api/tasks")
        .contentType(MediaType.APPLICATION_JSON)
        .body(Mono.just(requestDTO), TaskRequestDTO.class)
        .exchange()
        .expectStatus()
        .isOk()
        .expectBody()
        .jsonPath("$.id")
        .isEqualTo("task123")
        .jsonPath("$.title")
        .isEqualTo("Task Testing");

    verify(taskService, times(1)).createTask(requestDTO);
  }

  /** Should get task by id. @throws Exception the exception */
  @Test
  void shouldGetTaskById() throws Exception {
    doReturn(Mono.just(sampleTask)).when(taskService).getTaskById("task123");

    webTestClient
        .get()
        .uri("/api/tasks/task123")
        .exchange()
        .expectStatus()
        .isOk()
        .expectBody()
        .jsonPath("$.status")
        .isEqualTo("IN_PROGRESS");

    verify(taskService, times(1)).getTaskById("task123");
  }

  /** Should get filtered tasks. @throws Exception the exception */
  @Test
  void shouldGetFilteredTasks() throws Exception {
    List<TaskResponseDTO> tasks = List.of(sampleTask);
    doReturn(Flux.fromIterable(tasks))
        .when(taskService)
        .getFilteredTasks("proj123", "IN_PROGRESS", "HIGH", 0, 10);

    webTestClient
        .get()
        .uri(
            uriBuilder ->
                uriBuilder
                    .path("/api/tasks/proj123/tasks")
                    .queryParam("status", "IN_PROGRESS")
                    .queryParam("priority", "HIGH")
                    .queryParam("page", "0")
                    .queryParam("size", "10")
                    .build())
        .exchange()
        .expectStatus()
        .isOk()
        .expectBody()
        .jsonPath("$[0].id")
        .isEqualTo("task123");

    verify(taskService, times(1)).getFilteredTasks("proj123", "IN_PROGRESS", "HIGH", 0, 10);
  }

  /** Should update task status. @throws Exception the exception */
  @Test
  void shouldUpdateTaskStatus() throws Exception {
    doReturn(Mono.just(sampleTask)).when(taskService).updateStatus("task123", "COMPLETED");

    webTestClient
        .put()
        .uri("/api/tasks/task123/status?status=COMPLETED")
        .exchange()
        .expectStatus()
        .isOk()
        .expectBody()
        .jsonPath("$.status")
        .isEqualTo("IN_PROGRESS"); // Based on mock setup

    verify(taskService, times(1)).updateStatus("task123", "COMPLETED");
  }

  /** Should get overdue tasks. @throws Exception the exception */
  @Test
  void shouldGetOverdueTasks() throws Exception {
    List<TaskResponseDTO> tasks = List.of(sampleTask);
    doReturn(Flux.fromIterable(tasks)).when(taskService).getOverdue(0, 10);

    webTestClient
        .get()
        .uri(
            uriBuilder ->
                uriBuilder
                    .path("/api/tasks/overdue")
                    .queryParam("page", "0")
                    .queryParam("size", "10")
                    .build())
        .exchange()
        .expectStatus()
        .isOk()
        .expectBody()
        .jsonPath("$[0].id")
        .isEqualTo("task123")
        .jsonPath("$[0].title")
        .isEqualTo("Task Testing");

    verify(taskService, times(1)).getOverdue(0, 10);
  }

  /** Should get user assignments. @throws Exception the exception */
  @Test
  void shouldGetUserAssignments() throws Exception {
    List<TaskResponseDTO> tasks = List.of(sampleTask);
    doReturn(Flux.fromIterable(tasks)).when(taskService).getUserTasks("user123", 0, 10);

    webTestClient
        .get()
        .uri(
            uriBuilder ->
                uriBuilder
                    .path("/api/tasks/user123/assignments")
                    .queryParam("page", "0")
                    .queryParam("size", "10")
                    .build())
        .exchange()
        .expectStatus()
        .isOk()
        .expectBody()
        .jsonPath("$[0].id")
        .isEqualTo("task123")
        .jsonPath("$[0].title")
        .isEqualTo("Task Testing");

    verify(taskService, times(1)).getUserTasks("user123", 0, 10);
  }
}
