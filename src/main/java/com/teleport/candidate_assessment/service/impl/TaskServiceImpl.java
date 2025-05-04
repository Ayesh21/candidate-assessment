package com.teleport.candidate_assessment.service.impl;

import com.teleport.candidate_assessment.dto.TaskRequestDTO;
import com.teleport.candidate_assessment.dto.TaskResponseDTO;
import com.teleport.candidate_assessment.entity.Project;
import com.teleport.candidate_assessment.entity.Task;
import com.teleport.candidate_assessment.entity.User;
import com.teleport.candidate_assessment.exception.TaskException;
import com.teleport.candidate_assessment.exception.TaskNotFoundException;
import com.teleport.candidate_assessment.repository.ProjectRepository;
import com.teleport.candidate_assessment.repository.TaskRepository;
import com.teleport.candidate_assessment.repository.UserRepository;
import com.teleport.candidate_assessment.service.TaskService;
import com.teleport.candidate_assessment.transformer.TaskTransformer;
import com.teleport.candidate_assessment.utils.Constants;
import java.time.LocalDate;
import java.util.Set;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/** The type Task service. */
@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

  private final TaskRepository taskRepository;
  private final ProjectRepository projectRepository;
  private final UserRepository userRepository;

  /**
   * Create task.
   *
   * @param taskRequestDTO the task request dto
   * @return the task response dto
   */
  @Transactional
  @Override
  public TaskResponseDTO createTask(final TaskRequestDTO taskRequestDTO) {
    Project project = projectRepository.findById(taskRequestDTO.projectId()).orElseThrow();
    User assignee = userRepository.findById(taskRequestDTO.assigneeId()).orElseThrow();
    Task task = TaskTransformer.toEntity(taskRequestDTO, assignee, project);
    return TaskTransformer.toResponse(taskRepository.save(task));
  }

  /**
   * Gets task by id.
   *
   * @param taskId the task id
   * @return the task by id
   */
  @Override
  @Cacheable(value = "taskData", key = "#taskId")
  public TaskResponseDTO getTaskById(final String taskId) {
    return TaskResponseDTO.fromEntity(
        taskRepository.findById(taskId).orElseThrow(() -> new TaskNotFoundException(taskId)));
  }

  /**
   * Gets filtered tasks.
   *
   * @param projectId the project id
   * @param status the status
   * @param priority the priority
   * @param pageable the pageable
   * @return the filtered tasks
   */
  @Override
  public Page<TaskResponseDTO> getFilteredTasks(
      final String projectId, final String status, final String priority, final Pageable pageable) {
    return taskRepository
        .findByProjectIdAndStatusAndPriority(projectId, status, priority, pageable)
        .map(TaskResponseDTO::fromEntity);
  }

  // ----(Async status update + version check)
  /**
   * Update status.
   *
   * @param taskId the task id
   * @param status the status
   * @return the Updated tasks
   */
  @Transactional
  @Override
  @CachePut(value = "taskData", key = "#taskId")
  public TaskResponseDTO updateStatus(final String taskId, final String status) {
    Task task =
        taskRepository.findById(taskId).orElseThrow(() -> new TaskNotFoundException(taskId));

    String currentStatus = task.getStatus();
    String newStatus = status.toUpperCase();

    // Validate the new status
    if (!Set.of(
            Constants.Status.NEW.name(),
            Constants.Status.IN_PROGRESS.name(),
            Constants.Status.COMPLETED.name())
        .contains(newStatus)) {
      throw new TaskException("Invalid status: " + newStatus);
    }

    if (currentStatus.equals(newStatus)) {
      throw new TaskException("Task is already in status: " + currentStatus);
    }

    if (task.getStatus().equals(Constants.Status.COMPLETED.name())) {
      throw new TaskException("Already completed");
    }

    if (currentStatus.equals(Constants.Status.NEW.name())
        && newStatus.equals(Constants.Status.COMPLETED.name())) {
      throw new TaskException("Cannot directly move from NEW to COMPLETED. Use IN_PROGRESS first.");
    }

    task.setStatus(status.toUpperCase());
    return TaskTransformer.toResponse(taskRepository.save(task));
  }

  /**
   * Gets user tasks.
   *
   * @param userId the user id
   * @param pageable the pageable
   * @return the user tasks
   */
  @Override
  public Page<TaskResponseDTO> getUserTasks(String userId, Pageable pageable) {
    return taskRepository.findByAssigneeId(userId, pageable).map(TaskResponseDTO::fromEntity);
  }

  /**
   * Gets overdue.
   *
   * @param pageable the pageable
   * @return the overdue
   */
  @Override
  public Page<TaskResponseDTO> getOverdue(Pageable pageable) {
    return taskRepository
        .findOverdueTasks(LocalDate.now(), pageable)
        .map(TaskResponseDTO::fromEntity);
  }
}
