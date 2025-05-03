package com.teleport.candidate_assessment.dto;

import com.teleport.candidate_assessment.entity.Task;

import java.time.LocalDate;

public record TaskResponseDTO(
        String id,
        String title,
        String status,
        String priority,
        String assigneeId,
        String projectId,
        LocalDate dueDate
) {
    public static TaskResponseDTO fromEntity(Task task) {
        return new TaskResponseDTO(
                task.getId(),
                task.getTitle(),
                task.getStatus(),
                task.getPriority(),
                task.getAssignee().getId(),
                task.getProject().getId(),
                task.getDueDate()
        );
    }
}
