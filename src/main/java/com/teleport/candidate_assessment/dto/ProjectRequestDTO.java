package com.teleport.candidate_assessment.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/** The type Project request dto. */
public record ProjectRequestDTO(
    @NotBlank(message = "Project name is required") String name,
    @NotNull(message = "Owner ID is required") String ownerId) {}
