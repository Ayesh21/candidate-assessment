package com.teleport.candidate_assessment.controller;

import com.teleport.candidate_assessment.dto.UserRequestDTO;
import com.teleport.candidate_assessment.dto.UserResponseDTO;
import com.teleport.candidate_assessment.service.TaskService;
import com.teleport.candidate_assessment.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final TaskService taskService;

    @PostMapping
    public UserResponseDTO create(@Valid @RequestBody UserRequestDTO userRequestDTO) {
        return userService.create(userRequestDTO);
    }

    @GetMapping("/{userId}")
    public UserResponseDTO getUserById(@PathVariable String userId) {
        return userService.getUserById(userId);
    }
}
