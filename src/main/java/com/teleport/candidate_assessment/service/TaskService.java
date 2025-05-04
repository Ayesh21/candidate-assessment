package com.teleport.candidate_assessment.service;

import com.teleport.candidate_assessment.dto.TaskRequestDTO;
import com.teleport.candidate_assessment.dto.TaskResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/** The interface Task service. */
public interface TaskService {
  /**
   * Create task.
   *
   * @param taskRequestDTO the task request dto
   * @return the task response dto
   */
  TaskResponseDTO createTask(TaskRequestDTO taskRequestDTO);

  /**
   * Gets task by id.
   *
   * @param taskId the task id
   * @return the task by id
   */
  TaskResponseDTO getTaskById(String taskId);

  /**
   * Gets filtered tasks.
   *
   * @param projectId the project id
   * @param status the status
   * @param priority the priority
   * @param pageable the pageable
   * @return the filtered tasks
   */
  Page<TaskResponseDTO> getFilteredTasks(
      String projectId, String status, String priority, Pageable pageable);

  /**
   * Update status.
   *
   * @param taskId the task id
   * @param status the status
   * @return the Updated tasks
   */
  TaskResponseDTO updateStatus(String taskId, String status);

  /**
   * Gets user tasks.
   *
   * @param userId the user id
   * @param pageable the pageable
   * @return the user tasks
   */
  Page<TaskResponseDTO> getUserTasks(String userId, Pageable pageable);

  /**
   * Gets overdue.
   *
   * @param pageable the pageable
   * @return the overdue
   */
  Page<TaskResponseDTO> getOverdue(Pageable pageable);
}
