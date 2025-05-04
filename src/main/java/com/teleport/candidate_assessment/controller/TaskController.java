package com.teleport.candidate_assessment.controller;

import com.teleport.candidate_assessment.dto.TaskRequestDTO;
import com.teleport.candidate_assessment.dto.TaskResponseDTO;
import com.teleport.candidate_assessment.service.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/** The type Task controller. */
@RestController
@RequestMapping("api/tasks")
@RequiredArgsConstructor
@Tag(name = "Task Controller", description = "Task Endpoints")
public class TaskController {
  private final TaskService taskService;
  private static final Logger logger = LoggerFactory.getLogger(TaskController.class);

  /**
   * Create task task.
   *
   * @param taskRequestDTO the task request dto
   * @return the task response dto
   */
  @Operation(summary = "Create a task", description = "Creates a new task under a given project")
  @PostMapping
  public TaskResponseDTO createTask(@RequestBody final TaskRequestDTO taskRequestDTO) {
    logger.info("Creating Task: {}", taskRequestDTO);
    return taskService.createTask(taskRequestDTO);
  }

  /**
   * Gets task by id.
   *
   * @param taskId the task id
   * @return the task by id
   */
  @Operation(summary = "Get by ID", description = "Fetches tasks details by task Id")
  @GetMapping("/{taskId}")
  public TaskResponseDTO getTaskById(@PathVariable final String taskId) {
    logger.info("Fetching Task by Task ID: {}", taskId);
    return taskService.getTaskById(taskId);
  }

  /**
   * Gets filtered tasks.
   *
   * @param projectId the project id
   * @param status the status
   * @param priority the priority
   * @param page the page
   * @param size the size
   * @return the filtered tasks
   */
  @Operation(
      summary = "Get filtered tasks",
      description = "Returns tasks by project with optional filtering by status and priority")
  @GetMapping("/{projectId}/tasks")
  public Page<TaskResponseDTO> getFilteredTasks(
      @PathVariable final String projectId,
      @RequestParam final String status,
      @RequestParam final String priority,
      @RequestParam(name = "page", required = false, defaultValue = "0") final int page,
      @RequestParam(name = "size", required = false, defaultValue = "10") final int size) {
    logger.info(
        "Fetching filtered tasks - Project ID: {}, Status: {}, Priority: {}",
        projectId,
        status,
        priority);
    return taskService.getFilteredTasks(projectId, status, priority, page, size);
  }

  /**
   * Assignments page.
   *
   * @param userId the user id
   * @param page the page
   * @param size the size
   * @return the page
   */
  @Operation(
      summary = "View user assignments",
      description = "Returns all tasks assigned to the user")
  @GetMapping("/{userId}/assignments")
  public Page<TaskResponseDTO> assignments(
      @PathVariable final String userId,
      @RequestParam(name = "page", required = false, defaultValue = "0") final int page,
      @RequestParam(name = "size", required = false, defaultValue = "10") final int size) {
    logger.info("Fetching all tasks assigned to the user: {}", userId);
    return taskService.getUserTasks(userId, page, size);
  }

  /**
   * Update status.
   *
   * @param taskId the task id
   * @param status the status
   * @return the task response dto
   */
  @Operation(
      summary = "Update task status",
      description = "Updates the status of a task if it's not completed")
  @PutMapping("/{taskId}/status")
  public TaskResponseDTO updateStatus(
      @PathVariable final String taskId, @RequestParam final String status) {
    logger.info("Updates the status by Task ID: {}", taskId);
    return taskService.updateStatus(taskId, status);
  }

  /**
   * Overdue page.
   *
   * @param page the page
   * @param size the size
   * @return the page
   */
  @Operation(summary = "Get overdue tasks", description = "Fetches all tasks that are past due")
  @GetMapping("/overdue")
  public Page<TaskResponseDTO> overdue(
      @RequestParam(name = "page", required = false, defaultValue = "0") final int page,
      @RequestParam(name = "size", required = false, defaultValue = "10") final int size) {
    logger.info("Fetches all tasks that are past due");
    return taskService.getOverdue(page, size);
  }
}
