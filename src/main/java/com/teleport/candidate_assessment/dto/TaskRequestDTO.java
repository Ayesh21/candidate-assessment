package com.teleport.candidate_assessment.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

/** The type Task request dto. */
public record TaskRequestDTO(
    @NotBlank(message = "Title is required") String title,
    @NotNull(message = "Status is required") String status,
    @NotNull(message = "Priority is required") String priority,
    @NotNull(message = "Assignee ID is required") String assigneeId,
    @NotNull(message = "Project ID is required") String projectId,
    @NotNull(message = "Due date is required")
        @JsonDeserialize(using = LocalDateTimeDeserializer.class)
        @JsonSerialize(using = LocalDateTimeSerializer.class)
        LocalDateTime dueDate) {}
