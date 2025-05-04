package com.teleport.candidate_assessment.controller;

import static com.teleport.candidate_assessment.utils.LogConstant.CREATE_USER;
import static com.teleport.candidate_assessment.utils.LogConstant.GET_USER_BY_ID;
import static com.teleport.candidate_assessment.utils.TaskManagerConstant.USER_CONTROLLER_CREATE_ENDPOINT_DESCRIPTION;
import static com.teleport.candidate_assessment.utils.TaskManagerConstant.USER_CONTROLLER_CREATE_ENDPOINT_OUTPUT_OK;
import static com.teleport.candidate_assessment.utils.TaskManagerConstant.USER_CONTROLLER_CREATE_ENDPOINT_SUMMARY;
import static com.teleport.candidate_assessment.utils.TaskManagerConstant.USER_CONTROLLER_GET_ENDPOINT_DESCRIPTION;
import static com.teleport.candidate_assessment.utils.TaskManagerConstant.USER_CONTROLLER_GET_ENDPOINT_SUMMARY;
import static com.teleport.candidate_assessment.utils.UriConstant.USER_CONTROLLER_GET_ENDPOINT;
import static com.teleport.candidate_assessment.utils.UriConstant.USER_CONTROLLER_URL;

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
@RequestMapping(USER_CONTROLLER_URL)
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
      summary = USER_CONTROLLER_CREATE_ENDPOINT_SUMMARY,
      description = USER_CONTROLLER_CREATE_ENDPOINT_DESCRIPTION)
  public String create(@Valid @RequestBody final UserRequestDTO userRequestDTO) {
    logger.info(CREATE_USER, userRequestDTO);
    userService.create(userRequestDTO);
    return USER_CONTROLLER_CREATE_ENDPOINT_OUTPUT_OK;
  }

  /**
   * Gets user by id.
   *
   * @param userId the user id
   * @return the user by id
   */
  @GetMapping(USER_CONTROLLER_GET_ENDPOINT)
  @Operation(
      summary = USER_CONTROLLER_GET_ENDPOINT_SUMMARY,
      description = USER_CONTROLLER_GET_ENDPOINT_DESCRIPTION)
  public UserResponseDTO getUserById(@PathVariable final String userId) {
    logger.info(GET_USER_BY_ID, userId);
    return userService.getUserById(userId);
  }
}
