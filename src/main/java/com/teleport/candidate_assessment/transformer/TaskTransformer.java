package com.teleport.candidate_assessment.transformer;

import com.teleport.candidate_assessment.dto.TaskRequestDTO;
import com.teleport.candidate_assessment.dto.TaskResponseDTO;
import com.teleport.candidate_assessment.entity.Project;
import com.teleport.candidate_assessment.entity.Task;
import com.teleport.candidate_assessment.entity.User;
import com.teleport.candidate_assessment.utils.TaskManagerConstant;

/** The type Task transformer. */
public class TaskTransformer {
  /**
   * To entity task.
   *
   * @param dto the dto
   * @param assignee the assignee
   * @param project the project
   * @return the task
   */
  public static Task toEntity(
      final TaskRequestDTO dto, final User assignee, final Project project) {
    final Task task = new Task();
    task.setTitle(dto.title());
    task.setPriority(dto.priority().toUpperCase());
    task.setDueDate(dto.dueDate());
    task.setAssigneeId(assignee.getId());
    task.setProjectId(project.getId());
    task.setStatus(TaskManagerConstant.Status.NEW.name());
    return task;
  }

  /**
   * To response task response dto.
   *
   * @param task the task
   * @return the task response dto
   */
  public static TaskResponseDTO toResponse(final Task task) {
    return new TaskResponseDTO(
        task.getId(),
        task.getTitle(),
        task.getStatus(),
        task.getPriority(),
        task.getAssigneeId(),
        task.getProjectId(),
        task.getDueDate());
  }
}
