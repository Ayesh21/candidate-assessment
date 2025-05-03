package com.teleport.candidate_assessment.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record TaskRequestDTO(
        @NotBlank(message = "Title is required") String title,

        @NotNull(message = "Status is required") String status,

        @NotNull(message = "Priority is required") String priority,

        @NotNull(message = "Assignee ID is required") String assigneeId,

        @NotNull(message = "Project ID is required") String projectId,

        @NotNull(message = "Due date is required") LocalDate dueDate
) {}
