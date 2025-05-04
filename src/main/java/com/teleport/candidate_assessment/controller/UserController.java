package com.teleport.candidate_assessment.controller;

import static com.teleport.candidate_assessment.utils.LogConstant.CREATE_USER;
import static com.teleport.candidate_assessment.utils.LogConstant.GET_PROJECT_BY_ID;

import com.teleport.candidate_assessment.dto.UserRequestDTO;
import com.teleport.candidate_assessment.dto.UserResponseDTO;
import com.teleport.candidate_assessment.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/** The type User controller. */
@RestController
@RequestMapping("api/users")
@RequiredArgsConstructor
public class UserController {

  private final UserService userService;
  private static final Logger logger = LoggerFactory.getLogger(UserController.class);

  /**
   * Create user.
   *
   * @param userRequestDTO the user request dto
   */
  @PostMapping
  @Operation(
      summary = "Create a new user",
      description = "Registers a new user with username and email")
  public String create(@Valid @RequestBody final UserRequestDTO userRequestDTO) {
    logger.info(CREATE_USER, userRequestDTO);
    userService.create(userRequestDTO);
    return "OK";
  }

  /**
   * Gets user by id.
   *
   * @param userId the user id
   * @return the user by id
   */
  @GetMapping("/{userId}")
  @Operation(summary = "Get user by ID", description = "Fetches user details by their unique ID")
  public UserResponseDTO getUserById(@PathVariable final String userId) {
    logger.info(GET_PROJECT_BY_ID, userId);
    return userService.getUserById(userId);
  }
}
