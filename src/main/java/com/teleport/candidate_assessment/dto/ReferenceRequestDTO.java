package com.teleport.candidate_assessment.dto;

import jakarta.validation.constraints.NotBlank;

/** The type Reference request dto. */
public record ReferenceRequestDTO(
    @NotBlank(message = "User Id is required") String userId,
    @NotBlank(message = "Project Id is required") String projectId,
    @NotBlank(message = "Task Id is required") String taskId) {}
