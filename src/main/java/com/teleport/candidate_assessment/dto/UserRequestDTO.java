package com.teleport.candidate_assessment.dto;

import jakarta.validation.constraints.NotBlank;

public record UserRequestDTO(
        @NotBlank(message = "Username is required") String username,

        @NotBlank(message = "Email is required") String email
) {}
