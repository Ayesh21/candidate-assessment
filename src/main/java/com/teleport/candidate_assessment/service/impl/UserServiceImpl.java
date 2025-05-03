package com.teleport.candidate_assessment.service.impl;

import com.teleport.candidate_assessment.transformer.UserTransformer;
import com.teleport.candidate_assessment.dto.UserRequestDTO;
import com.teleport.candidate_assessment.dto.UserResponseDTO;
import com.teleport.candidate_assessment.entity.User;
import com.teleport.candidate_assessment.repository.UserRepository;
import com.teleport.candidate_assessment.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    @Override
    public UserResponseDTO create(UserRequestDTO userRequestDTO) {
        User user = UserTransformer.toEntity(userRequestDTO);
        return UserTransformer.toResponse(userRepository.save(user));
    }

    @Override
    public UserResponseDTO getUserById(String userId) {
        return UserResponseDTO.fromEntity(userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("User not found")));
    }
}
