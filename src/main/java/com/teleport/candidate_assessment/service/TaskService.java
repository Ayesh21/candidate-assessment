package com.teleport.candidate_assessment.service;

import com.teleport.candidate_assessment.dto.TaskRequestDTO;
import com.teleport.candidate_assessment.dto.TaskResponseDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/** The interface Task service. */
public interface TaskService {
  /**
   * Create task.
   *
   * @param taskRequestDTO the task request dto
   * @return the task response dto
   */
  Mono<TaskResponseDTO> createTask(TaskRequestDTO taskRequestDTO);

  /**
   * Gets task by id.
   *
   * @param taskId the task id
   * @return the task by id
   */
  Mono<TaskResponseDTO> getTaskById(String taskId);

  /**
   * Gets filtered tasks.
   *
   * @param projectId the project id
   * @param status the status
   * @param priority the priority
   * @return the filtered tasks
   */
  Flux<TaskResponseDTO> getFilteredTasks(
      final String projectId,
      final String status,
      final String priority,
      final int page,
      final int size);

  /**
   * Update status.
   *
   * @param taskId the task id
   * @param status the status
   * @return the Updated tasks
   */
  Mono<TaskResponseDTO> updateStatus(String taskId, String status);

  /**
   * Gets user tasks.
   *
   * @param userId the user id
   * @return the user tasks
   */
  Flux<TaskResponseDTO> getUserTasks(final String userId, final int page, final int size);

  /**
   * Gets overdue.
   *
   * @return the overdue
   * @param page the page
   * @param size the size
   */
  Flux<TaskResponseDTO> getOverdue(final int page, final int size);
}
