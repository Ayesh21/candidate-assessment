package com.teleport.candidate_assessment.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
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
import org.springframework.data.domain.*;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

/** The type Task controller test. */
@ExtendWith(MockitoExtension.class)
class TaskControllerTest {

  @Mock private TaskService taskService;

  @InjectMocks private TaskController taskController;

  private MockMvc mockMvc;
  private final ObjectMapper objectMapper = new ObjectMapper();

  private TaskResponseDTO sampleTask;
  private Pageable pageable;

  /** Sets up. */
  @BeforeEach
  void setUp() {
    PageableHandlerMethodArgumentResolver pageableResolver =
        new PageableHandlerMethodArgumentResolver();
    pageableResolver.setMaxPageSize(15);

    mockMvc =
        MockMvcBuilders.standaloneSetup(taskController)
            .setCustomArgumentResolvers(pageableResolver)
            .build();

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

  /**
   * Should create task.
   *
   * @throws Exception the exception
   */
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
    when(taskService.createTask(requestDTO)).thenReturn(sampleTask);

    mockMvc
        .perform(
            post("/api/tasks")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDTO)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value("task123"))
        .andExpect(jsonPath("$.title").value("Task Testing"));
  }

  /**
   * Should get task by id.
   *
   * @throws Exception the exception
   */
  @Test
  void shouldGetTaskById() throws Exception {
    when(taskService.getTaskById("task123")).thenReturn(sampleTask);

    mockMvc
        .perform(get("/api/tasks/task123"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.status").value("IN_PROGRESS"));
  }

  /**
   * Should get filtered tasks.
   *
   * @throws Exception the exception
   */
  @Test
  void shouldGetFilteredTasks() throws Exception {
    Page<TaskResponseDTO> page = new PageImpl<>(List.of(sampleTask), pageable, 1);

    when(taskService.getFilteredTasks("proj123", "IN_PROGRESS", "HIGH", 0, 10)).thenReturn(page);

    mockMvc
        .perform(
            get("/api/tasks/proj123/tasks")
                .param("status", "IN_PROGRESS")
                .param("priority", "HIGH")
                .param("page", "0")
                .param("size", "10")
                .param("sort", "dueDate,desc"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.content[0].id").value("task123"));
  }

  /**
   * Should update task status.
   *
   * @throws Exception the exception
   */
  @Test
  void shouldUpdateTaskStatus() throws Exception {
    when(taskService.updateStatus("task123", "COMPLETED")).thenReturn(sampleTask);

    mockMvc
        .perform(put("/api/tasks/task123/status").param("status", "COMPLETED"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.status").value("IN_PROGRESS")); // from mock setup
  }

  /**
   * Should get overdue tasks.
   *
   * @throws Exception the exception
   */
  @Test
  void shouldGetOverdueTasks() throws Exception {
    Page<TaskResponseDTO> page = new PageImpl<>(List.of(sampleTask), pageable, 1);

    when(taskService.getOverdue(0, 10)).thenReturn(page);

    mockMvc
        .perform(get("/api/tasks/overdue").param("page", "0").param("size", "10"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.content[0].id").value("task123"))
        .andExpect(jsonPath("$.content[0].title").value("Task Testing"));
  }

  /**
   * Should get user assignments.
   *
   * @throws Exception the exception
   */
  @Test
  void shouldGetUserAssignments() throws Exception {
    Page<TaskResponseDTO> page = new PageImpl<>(List.of(sampleTask), pageable, 1);

    when(taskService.getUserTasks("user123", 0, 10)).thenReturn(page);

    mockMvc
        .perform(get("/api/tasks/user123/assignments").param("page", "0").param("size", "10"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.content[0].id").value("task123"))
        .andExpect(jsonPath("$.content[0].title").value("Task Testing"));
  }
}
