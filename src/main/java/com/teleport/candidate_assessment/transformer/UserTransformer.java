package com.teleport.candidate_assessment.transformer;

import com.teleport.candidate_assessment.dto.UserRequestDTO;
import com.teleport.candidate_assessment.dto.UserResponseDTO;
import com.teleport.candidate_assessment.entity.User;

public class UserTransformer {

    public static User toEntity(UserRequestDTO dto) {
        User user = new User();
        user.setName(dto.username());
        user.setEmail(dto.email());
        return user;
    }

    public static UserResponseDTO toResponse(User user) {
        return new UserResponseDTO(user.getId(), user.getName(), user.getEmail());
    }
}
