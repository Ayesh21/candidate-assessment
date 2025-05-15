package com.teleport.candidate_assessment.controller;

import static com.teleport.candidate_assessment.utils.LogConstant.CREATE_TASK;
import static com.teleport.candidate_assessment.utils.LogConstant.GET_ASSIGNMENT_BY_USER_ID;
import static com.teleport.candidate_assessment.utils.LogConstant.GET_FILTERED_TASK_BY_ID;
import static com.teleport.candidate_assessment.utils.LogConstant.GET_OVERDUE_TASKS_BY_CURRENT_DATE;
import static com.teleport.candidate_assessment.utils.LogConstant.GET_TASK_BY_ID;
import static com.teleport.candidate_assessment.utils.LogConstant.UPDATE_STATUS_BY_TASK_ID;
import static com.teleport.candidate_assessment.utils.TaskManagerConstant.FILTERED_TASK_CONTROLLER_GET_ENDPOINT_DESCRIPTION;
import static com.teleport.candidate_assessment.utils.TaskManagerConstant.FILTERED_TASK_CONTROLLER_GET_ENDPOINT_SUMMARY;
import static com.teleport.candidate_assessment.utils.TaskManagerConstant.TASK_CONTROLLER_CREATE_ENDPOINT_DESCRIPTION;
import static com.teleport.candidate_assessment.utils.TaskManagerConstant.TASK_CONTROLLER_CREATE_ENDPOINT_SUMMARY;
import static com.teleport.candidate_assessment.utils.TaskManagerConstant.TASK_CONTROLLER_GET_ASSIGNMENT_ENDPOINT_DESCRIPTION;
import static com.teleport.candidate_assessment.utils.TaskManagerConstant.TASK_CONTROLLER_GET_ASSIGNMENT_ENDPOINT_SUMMARY;
import static com.teleport.candidate_assessment.utils.TaskManagerConstant.TASK_CONTROLLER_GET_ENDPOINT_DESCRIPTION;
import static com.teleport.candidate_assessment.utils.TaskManagerConstant.TASK_CONTROLLER_GET_ENDPOINT_SUMMARY;
import static com.teleport.candidate_assessment.utils.TaskManagerConstant.TASK_CONTROLLER_GET_OVERDUE_TASKS_ENDPOINT_DESCRIPTION;
import static com.teleport.candidate_assessment.utils.TaskManagerConstant.TASK_CONTROLLER_GET_OVERDUE_TASKS_ENDPOINT_SUMMARY;
import static com.teleport.candidate_assessment.utils.TaskManagerConstant.TASK_CONTROLLER_UPDATE_STATUS_ENDPOINT_DESCRIPTION;
import static com.teleport.candidate_assessment.utils.TaskManagerConstant.TASK_CONTROLLER_UPDATE_STATUS_ENDPOINT_SUMMARY;
import static com.teleport.candidate_assessment.utils.UriConstant.FILTERED_TASK_CONTROLLER_GET_ENDPOINT;
import static com.teleport.candidate_assessment.utils.UriConstant.TASK_CONTROLLER_GET_ASSIGNMENT_ENDPOINT;
import static com.teleport.candidate_assessment.utils.UriConstant.TASK_CONTROLLER_GET_ENDPOINT;
import static com.teleport.candidate_assessment.utils.UriConstant.TASK_CONTROLLER_GET_OVERDUE_TASKS_ENDPOINT;
import static com.teleport.candidate_assessment.utils.UriConstant.TASK_CONTROLLER_UPDATE_STATUS_ENDPOINT;
import static com.teleport.candidate_assessment.utils.UriConstant.TASK_CONTROLLER_URL;

import com.teleport.candidate_assessment.dto.TaskRequestDTO;
import com.teleport.candidate_assessment.dto.TaskResponseDTO;
import com.teleport.candidate_assessment.service.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/** The type Task controller. */
@RestController
@RequestMapping(TASK_CONTROLLER_URL)
@RequiredArgsConstructor
public class TaskController {
  private final TaskService taskService;
  private static final Logger logger = LoggerFactory.getLogger(TaskController.class);

  /**
   * Create task task.
   *
   * @param taskRequestDTO the task request dto
   * @return the task response dto
   */
  @Operation(
      summary = TASK_CONTROLLER_CREATE_ENDPOINT_SUMMARY,
      description = TASK_CONTROLLER_CREATE_ENDPOINT_DESCRIPTION)
  @PostMapping
  public Mono<TaskResponseDTO> createTask(@RequestBody TaskRequestDTO taskRequestDTO) {
    logger.info(CREATE_TASK, taskRequestDTO);
    return taskService.createTask(taskRequestDTO);
  }

  /**
   * Gets task by id.
   *
   * @param taskId the task id
   * @return the task by id
   */
  @GetMapping(TASK_CONTROLLER_GET_ENDPOINT)
  @Operation(
      summary = TASK_CONTROLLER_GET_ENDPOINT_SUMMARY,
      description = TASK_CONTROLLER_GET_ENDPOINT_DESCRIPTION)
  public Mono<TaskResponseDTO> getTaskById(@PathVariable String taskId) {
    logger.info(GET_TASK_BY_ID, taskId);
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
      summary = FILTERED_TASK_CONTROLLER_GET_ENDPOINT_SUMMARY,
      description = FILTERED_TASK_CONTROLLER_GET_ENDPOINT_DESCRIPTION)
  @GetMapping(FILTERED_TASK_CONTROLLER_GET_ENDPOINT)
  public Flux<TaskResponseDTO> getFilteredTasks(
      @PathVariable final String projectId,
      @RequestParam final String status,
      @RequestParam final String priority,
      @RequestParam(name = "page", required = false, defaultValue = "0") final int page,
      @RequestParam(name = "size", required = false, defaultValue = "10") final int size) {
    logger.info(GET_FILTERED_TASK_BY_ID, projectId, status, priority);
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
      summary = TASK_CONTROLLER_GET_ASSIGNMENT_ENDPOINT_SUMMARY,
      description = TASK_CONTROLLER_GET_ASSIGNMENT_ENDPOINT_DESCRIPTION)
  @GetMapping(TASK_CONTROLLER_GET_ASSIGNMENT_ENDPOINT)
  public Flux<TaskResponseDTO> assignments(
      @PathVariable final String userId,
      @RequestParam(name = "page", required = false, defaultValue = "0") final int page,
      @RequestParam(name = "size", required = false, defaultValue = "10") final int size) {
    logger.info(GET_ASSIGNMENT_BY_USER_ID, userId);
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
      summary = TASK_CONTROLLER_UPDATE_STATUS_ENDPOINT_SUMMARY,
      description = TASK_CONTROLLER_UPDATE_STATUS_ENDPOINT_DESCRIPTION)
  @PutMapping(TASK_CONTROLLER_UPDATE_STATUS_ENDPOINT)
  public Mono<TaskResponseDTO> updateStatus(
      @PathVariable final String taskId, @RequestParam final String status) {
    logger.info(UPDATE_STATUS_BY_TASK_ID, taskId);
    return taskService.updateStatus(taskId, status);
  }

  /**
   * Overdue page.
   *
   * @param page the page
   * @param size the size
   * @return the page
   */
  @Operation(
      summary = TASK_CONTROLLER_GET_OVERDUE_TASKS_ENDPOINT_SUMMARY,
      description = TASK_CONTROLLER_GET_OVERDUE_TASKS_ENDPOINT_DESCRIPTION)
  @GetMapping(TASK_CONTROLLER_GET_OVERDUE_TASKS_ENDPOINT)
  public Flux<TaskResponseDTO> overdue(
      @RequestParam(name = "page", required = false, defaultValue = "0") final int page,
      @RequestParam(name = "size", required = false, defaultValue = "10") final int size) {
    logger.info(GET_OVERDUE_TASKS_BY_CURRENT_DATE);
    return taskService.getOverdue(page, size);
  }
}
