package com.teleport.candidate_assessment.service;

import com.teleport.candidate_assessment.dto.TaskRequestDTO;
import com.teleport.candidate_assessment.dto.TaskResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TaskService {
    TaskResponseDTO createTask(TaskRequestDTO taskRequestDTO);
    Page<TaskResponseDTO> getFilteredTasks(String projectId, String status, String priority, Pageable pageable);
    void updateStatus(String taskId, String status);
    Page<TaskResponseDTO> getUserTasks(String userId, Pageable pageable);
    public Page<TaskResponseDTO> getOverdue(Pageable pageable);
}
