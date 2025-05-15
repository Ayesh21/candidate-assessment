package com.teleport.candidate_assessment.service.impl;

import static com.teleport.candidate_assessment.utils.LogConstant.TASKS_STATUS;

import com.teleport.candidate_assessment.dto.TaskRequestDTO;
import com.teleport.candidate_assessment.dto.TaskResponseDTO;
import com.teleport.candidate_assessment.entity.Task;
import com.teleport.candidate_assessment.exception.TaskException;
import com.teleport.candidate_assessment.exception.TaskNotFoundException;
import com.teleport.candidate_assessment.repository.ProjectRepository;
import com.teleport.candidate_assessment.repository.TaskRepository;
import com.teleport.candidate_assessment.repository.UserRepository;
import com.teleport.candidate_assessment.service.TaskService;
import com.teleport.candidate_assessment.transformer.TaskTransformer;
import com.teleport.candidate_assessment.utils.TaskManagerConstant;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/** The type Task service. */
@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

  private final TaskRepository taskRepository;
  private final ProjectRepository projectRepository;
  private final UserRepository userRepository;

  private static final Logger logger = LoggerFactory.getLogger(TaskServiceImpl.class);

  /**
   * Create task.
   *
   * @param taskRequestDTO the task request dto
   * @return the task response dto
   */
  @Override
  public Mono<TaskResponseDTO> createTask(final TaskRequestDTO taskRequestDTO) {
    return Mono.zip(
            projectRepository
                .findById(taskRequestDTO.projectId())
                .switchIfEmpty(Mono.error(new TaskException("Project not found"))),
            userRepository
                .findById(taskRequestDTO.assigneeId())
                .switchIfEmpty(Mono.error(new TaskException("Assignee not found"))))
        .flatMap(
            tuple -> {
              Task task = TaskTransformer.toEntity(taskRequestDTO, tuple.getT2(), tuple.getT1());
              String id = UUID.randomUUID().toString();
              task.setId(id);

              return taskRepository
                  .createTask(id, task.getTitle(), task.getAssigneeId(), task.getProjectId(),
                      task.getPriority(), task.getDueDate(), task.getStatus())
                  .thenReturn(task);
            })
        .map(TaskTransformer::toResponse);
  }

  /**
   * Gets task by id.
   *
   * @param taskId the task id
   * @return the task by id
   */
  @Override
  public Mono<TaskResponseDTO> getTaskById(final String taskId) {
    return taskRepository
        .findById(taskId)
        .switchIfEmpty(Mono.error(new TaskNotFoundException(taskId)))
        .map(TaskResponseDTO::fromEntity);
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
  @Override
  public Flux<TaskResponseDTO> getFilteredTasks(
      final String projectId,
      final String status,
      final String priority,
      final int page,
      final int size) {
    return taskRepository
        .findByProjectIdAndStatusAndPriority(projectId, status, priority)
        .skip((long) page * size)
        .take(size)
        .map(TaskResponseDTO::fromEntity);
  }

  /**
   * Update status.
   *
   * @param taskId the task id
   * @param status the status
   * @return the Updated tasks
   */
  @Override
  public Mono<TaskResponseDTO> updateStatus(final String taskId, final String status) {
    return taskRepository
        .findById(taskId)
        .switchIfEmpty(Mono.error(new TaskNotFoundException(taskId)))
        .flatMap(
            task -> {
              final String currentStatus = task.getStatus();
              final String newStatus = status.toUpperCase();

              if (!Set.of(
                      TaskManagerConstant.Status.NEW.name(),
                      TaskManagerConstant.Status.IN_PROGRESS.name(),
                      TaskManagerConstant.Status.COMPLETED.name())
                  .contains(newStatus)) {
                throw new TaskException("Invalid status: " + newStatus);
              }

              if (currentStatus.equals(newStatus)) {
                throw new TaskException("Task is already in status: " + currentStatus);
              }

              if (task.getStatus().equals(TaskManagerConstant.Status.COMPLETED.name())) {
                throw new TaskException("Already completed");
              }

              if (currentStatus.equals(TaskManagerConstant.Status.NEW.name())
                  && newStatus.equals(TaskManagerConstant.Status.COMPLETED.name())) {
                throw new TaskException(
                    "Cannot directly move from NEW to COMPLETED. Use IN_PROGRESS first.");
              }
              task.setStatus(newStatus);
              logger.info(TASKS_STATUS, newStatus);

              return taskRepository.save(task).map(TaskTransformer::toResponse);
            });
  }

  /**
   * Gets user tasks.
   *
   * @param userId the user id
   * @param page the page
   * @param size the size
   * @return the user tasks
   */
  @Override
  public Flux<TaskResponseDTO> getUserTasks(final String userId, final int page, final int size) {
    return taskRepository
        .findByAssigneeId(userId)
        .skip((long) page * size)
        .take(size)
        .map(TaskResponseDTO::fromEntity);
  }

  /**
   * Gets overdue.
   *
   * @param page the page
   * @param size the size
   * @return the overdue
   */
  @Override
  public Flux<TaskResponseDTO> getOverdue(final int page, final int size) {
    return taskRepository
        .findOverdueTasks(LocalDateTime.now())
        .skip((long) page * size)
        .take(size)
        .map(TaskResponseDTO::fromEntity);
  }
}
