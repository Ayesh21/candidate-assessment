package com.teleport.candidate_assessment.dto;

import com.teleport.candidate_assessment.entity.User;

public record UserResponseDTO(
        String id,
        String username,
        String email
) {
    // Helper method to convert from User entity to UserResponseDTO
    public static UserResponseDTO fromEntity(User user) {
        return new UserResponseDTO(
                user.getId(),
                user.getName(),
                user.getEmail()
        );
    }
}
