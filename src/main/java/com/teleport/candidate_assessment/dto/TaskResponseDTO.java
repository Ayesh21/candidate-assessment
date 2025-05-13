package com.teleport.candidate_assessment.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.teleport.candidate_assessment.entity.Task;
import java.time.LocalDateTime;

/** The type Task response dto. */
public record TaskResponseDTO(
    String id,
    String title,
    String status,
    String priority,
    String assigneeId,
    String projectId,
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
        @JsonSerialize(using = LocalDateTimeSerializer.class)
        LocalDateTime dueDate) {

  public static TaskResponseDTO fromEntity(Task task) {

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
