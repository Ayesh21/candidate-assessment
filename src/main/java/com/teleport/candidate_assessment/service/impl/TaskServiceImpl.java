package com.teleport.candidate_assessment.service.impl;

import com.teleport.candidate_assessment.dto.TaskRequestDTO;
import com.teleport.candidate_assessment.dto.TaskResponseDTO;
import com.teleport.candidate_assessment.entity.Project;
import com.teleport.candidate_assessment.entity.Task;
import com.teleport.candidate_assessment.entity.User;
import com.teleport.candidate_assessment.repository.ProjectRepository;
import com.teleport.candidate_assessment.repository.TaskRepository;
import com.teleport.candidate_assessment.repository.UserRepository;
import com.teleport.candidate_assessment.service.TaskService;
import com.teleport.candidate_assessment.transformer.TaskTransformer;
import com.teleport.candidate_assessment.utils.Constants;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;
    @Override
    public TaskResponseDTO createTask(TaskRequestDTO taskRequestDTO) {
        Project project = projectRepository.findById(taskRequestDTO.projectId()).orElseThrow();
        User assignee = userRepository.findById(taskRequestDTO.assigneeId()).orElseThrow();
        Task task = TaskTransformer.toEntity(taskRequestDTO,assignee,project);
        return TaskTransformer.toResponse(taskRepository.save(task));
    }

    @Override
    public Page<TaskResponseDTO> getFilteredTasks(String projectId, String status, String priority, Pageable pageable) {
        return taskRepository
                .findByProjectIdAndStatusAndPriority(projectId, status, priority, pageable)
                .map(TaskResponseDTO::fromEntity);
    }

    // ----(Async status update + version check)
    @Override
    public void updateStatus(String taskId, String status) {
        Task task = taskRepository.findById(taskId).orElseThrow();
        if (task.getStatus().equals(Constants.Status.COMPLETED)) {
            throw new IllegalStateException("Already completed");
        }
        task.setStatus(status.toUpperCase());
        taskRepository.save(task);

    }

    @Override
    public Page<TaskResponseDTO> getUserTasks(String userId, Pageable pageable) {
        return taskRepository.findByAssigneeId(userId, pageable)
                .map(TaskResponseDTO::fromEntity);
    }

    @Override
    public Page<TaskResponseDTO> getOverdue(Pageable pageable) {
        return taskRepository.findOverdueTasks(LocalDate.now(), pageable)
                .map(TaskResponseDTO::fromEntity);
    }
}
