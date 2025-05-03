package com.teleport.candidate_assessment.service;

import com.teleport.candidate_assessment.dto.UserRequestDTO;
import com.teleport.candidate_assessment.dto.UserResponseDTO;

public interface UserService {
    UserResponseDTO create(UserRequestDTO userRequestDTO);
    UserResponseDTO getUserById(String userId);
}
