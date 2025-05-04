package com.teleport.candidate_assessment.service.impl;

import com.teleport.candidate_assessment.config.CacheConfig;
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
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

  private final TaskRepository taskRepository;
  private final ProjectRepository projectRepository;
  private final UserRepository userRepository;

  @Override
  public TaskResponseDTO createTask(final TaskRequestDTO taskRequestDTO) {
    Project project = projectRepository.findById(taskRequestDTO.projectId()).orElseThrow();
    User assignee = userRepository.findById(taskRequestDTO.assigneeId()).orElseThrow();
    Task task = TaskTransformer.toEntity(taskRequestDTO, assignee, project);
    return TaskTransformer.toResponse(taskRepository.save(task));
  }

  @Override
  @Cacheable(value = "taskData", key = "#taskId")
  public TaskResponseDTO getTaskById(final String taskId) {
    return TaskResponseDTO.fromEntity(
        taskRepository.findById(taskId).orElseThrow(() -> new TaskNotFoundException(taskId)));
  }

  @Override
  public Page<TaskResponseDTO> getFilteredTasks(
      final String projectId, final String status, final String priority, final Pageable pageable) {
    return taskRepository
        .findByProjectIdAndStatusAndPriority(projectId, status, priority, pageable)
        .map(TaskResponseDTO::fromEntity);
  }

  // ----(Async status update + version check)
  @Override
  @CachePut(value = "taskData", key = "#taskId")
  public void updateStatus(final String taskId, final String status) {
    Task task = taskRepository.findById(taskId).orElseThrow();
    if (task.getStatus().equals(Constants.Status.COMPLETED)) {
      throw new TaskException("Already completed");
    }
    task.setStatus(status.toUpperCase());
    taskRepository.save(task);
  }

  @Override
  public Page<TaskResponseDTO> getUserTasks(String userId, Pageable pageable) {
    return taskRepository.findByAssigneeId(userId, pageable).map(TaskResponseDTO::fromEntity);
  }

  @Override
  public Page<TaskResponseDTO> getOverdue(Pageable pageable) {
    return taskRepository
        .findOverdueTasks(LocalDate.now(), pageable)
        .map(TaskResponseDTO::fromEntity);
  }
}
