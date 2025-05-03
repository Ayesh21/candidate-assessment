package com.teleport.candidate_assessment.transformer;

import com.teleport.candidate_assessment.dto.TaskRequestDTO;
import com.teleport.candidate_assessment.dto.TaskResponseDTO;
import com.teleport.candidate_assessment.entity.Project;
import com.teleport.candidate_assessment.entity.Task;
import com.teleport.candidate_assessment.entity.User;

public class TaskTransformer {
    public static Task toEntity(TaskRequestDTO dto, User assignee, Project project) {
        Task task = new Task();
        task.setTitle(dto.title());
        task.setPriority(dto.priority());
        task.setDueDate(dto.dueDate());
        task.setAssignee(assignee);
        task.setProject(project);
        task.setStatus("NEW"); // default status
        return task;
    }

    public static TaskResponseDTO toResponse(Task task) {
        return new TaskResponseDTO(
                task.getId(),
                task.getTitle(),
                task.getStatus(),
                task.getPriority(),
                task.getAssignee() != null ? task.getAssignee().getId() : null,
                task.getProject() != null ? task.getProject().getId() : null,
                task.getDueDate()
        );
    }
}
