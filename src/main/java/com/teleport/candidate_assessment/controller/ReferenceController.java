package com.teleport.candidate_assessment.controller;

import static com.teleport.candidate_assessment.utils.LogConstant.CREATE_REFERENCE;
import static com.teleport.candidate_assessment.utils.LogConstant.GET_REFERENCE_BY_PROJECT_ID;
import static com.teleport.candidate_assessment.utils.LogConstant.GET_REFERENCE_BY_TASK_ID;
import static com.teleport.candidate_assessment.utils.LogConstant.GET_REFERENCE_BY_USER_ID;
import static com.teleport.candidate_assessment.utils.TaskManagerConstant.REFERENCE_CONTROLLER_CREATE_DESCRIPTION;
import static com.teleport.candidate_assessment.utils.TaskManagerConstant.REFERENCE_CONTROLLER_CREATE_ENDPOINT_SUMMARY;
import static com.teleport.candidate_assessment.utils.TaskManagerConstant.REFERENCE_CONTROLLER_GET_PROJECT_ID_ENDPOINT_SUMMARY;
import static com.teleport.candidate_assessment.utils.TaskManagerConstant.REFERENCE_CONTROLLER_GET_TASK_ID_DESCRIPTION;
import static com.teleport.candidate_assessment.utils.TaskManagerConstant.REFERENCE_CONTROLLER_GET_TASK_ID_ENDPOINT_SUMMARY;
import static com.teleport.candidate_assessment.utils.TaskManagerConstant.REFERENCE_CONTROLLER_GET_USERID_DESCRIPTION;
import static com.teleport.candidate_assessment.utils.TaskManagerConstant.REFERENCE_CONTROLLER_GET_USER_ID_DESCRIPTION;
import static com.teleport.candidate_assessment.utils.TaskManagerConstant.REFERENCE_CONTROLLER_GET_USER_ID_ENDPOINT_SUMMARY;

import com.teleport.candidate_assessment.dto.ReferenceRequestDTO;
import com.teleport.candidate_assessment.dto.ReferenceResponseDTO;
import com.teleport.candidate_assessment.service.ReferenceService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/** The type Reference controller. */
@RestController
@RequestMapping("/api/references")
@RequiredArgsConstructor
public class ReferenceController {

  private final ReferenceService service;
  private static final Logger logger = LoggerFactory.getLogger(ReferenceController.class);

  /**
   * Create mono.
   *
   * @param referenceRequestDTO the reference request dto
   * @return the mono
   */
  @PostMapping
  @Operation(
      summary = REFERENCE_CONTROLLER_CREATE_ENDPOINT_SUMMARY,
      description = REFERENCE_CONTROLLER_CREATE_DESCRIPTION)
  public Mono<ReferenceResponseDTO> create(
      @Valid @RequestBody ReferenceRequestDTO referenceRequestDTO) {
    logger.info(CREATE_REFERENCE, referenceRequestDTO);
    return service.createReference(referenceRequestDTO);
  }

  /**
   * Gets by user id.
   *
   * @param userId the user id
   * @return the by user id
   */
  @GetMapping("/user/{userId}")
  @Operation(
      summary = REFERENCE_CONTROLLER_GET_USER_ID_ENDPOINT_SUMMARY,
      description = REFERENCE_CONTROLLER_GET_USER_ID_DESCRIPTION)
  public Flux<ReferenceResponseDTO> getByUserId(@PathVariable String userId) {
    logger.info(GET_REFERENCE_BY_USER_ID, userId);
    return service.getByUserId(userId);
  }

  /**
   * Gets by project id.
   *
   * @param projectId the project id
   * @return the by project id
   */
  @GetMapping("/project/{projectId}")
  @Operation(
      summary = REFERENCE_CONTROLLER_GET_PROJECT_ID_ENDPOINT_SUMMARY,
      description = REFERENCE_CONTROLLER_GET_USERID_DESCRIPTION)
  public Flux<ReferenceResponseDTO> getByProjectId(@PathVariable String projectId) {
    logger.info(GET_REFERENCE_BY_PROJECT_ID, projectId);
    return service.getByProjectId(projectId);
  }

  /**
   * Gets by task id.
   *
   * @param taskId the task id
   * @return the by task id
   */
  @GetMapping("/task/{taskId}")
  @Operation(
      summary = REFERENCE_CONTROLLER_GET_TASK_ID_ENDPOINT_SUMMARY,
      description = REFERENCE_CONTROLLER_GET_TASK_ID_DESCRIPTION)
  public Flux<ReferenceResponseDTO> getByTaskId(@PathVariable String taskId) {
    logger.info(GET_REFERENCE_BY_TASK_ID, taskId);
    return service.getByTaskId(taskId);
  }
}
