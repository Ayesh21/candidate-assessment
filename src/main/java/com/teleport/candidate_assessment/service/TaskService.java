package com.teleport.candidate_assessment.service;

import com.teleport.candidate_assessment.dto.TaskRequestDTO;
import com.teleport.candidate_assessment.dto.TaskResponseDTO;
import org.springframework.data.domain.Page;

/** The interface Task service. */
public interface TaskService {
  /**
   * Create task.
   *
   * @param taskRequestDTO the task request dto
   * @return the task response dto
   */
  TaskResponseDTO createTask(final TaskRequestDTO taskRequestDTO);

  /**
   * Gets task by id.
   *
   * @param taskId the task id
   * @return the task by id
   */
  TaskResponseDTO getTaskById(final String taskId);

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
  Page<TaskResponseDTO> getFilteredTasks(
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
  TaskResponseDTO updateStatus(final String taskId, final String status);

  /**
   * Gets user tasks.
   *
   * @param userId the user id
   * @param page the page
   * @param size the size
   * @return the user tasks
   */
  Page<TaskResponseDTO> getUserTasks(final String userId, final int page, final int size);

  /**
   * Gets overdue.
   *
   * @param page the page
   * @param size the size
   * @return the overdue
   */
  Page<TaskResponseDTO> getOverdue(final int page, final int size);
}
