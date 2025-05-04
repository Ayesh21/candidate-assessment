package com.teleport.candidate_assessment.controller;

import com.teleport.candidate_assessment.dto.UserRequestDTO;
import com.teleport.candidate_assessment.dto.UserResponseDTO;
import com.teleport.candidate_assessment.service.TaskService;
import com.teleport.candidate_assessment.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @PostMapping
    @Operation(summary = "Create a new user", description = "Registers a new user with username and email")
    public UserResponseDTO create(@Valid @RequestBody UserRequestDTO userRequestDTO) {
        logger.info("Creating User: {}", userRequestDTO);
        return userService.create(userRequestDTO);
    }

    @GetMapping("/{userId}")
    @Operation(summary = "Get user by ID", description = "Fetches user details by their unique ID")
    public UserResponseDTO getUserById(@PathVariable String userId) {
        logger.info("Fetching User by user ID: {}", userId);
        return userService.getUserById(userId);
    }
}
