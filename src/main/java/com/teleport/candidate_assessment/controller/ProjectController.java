package com.teleport.candidate_assessment.controller;

import static com.teleport.candidate_assessment.utils.LogConstant.CREATE_PROJECT;
import static com.teleport.candidate_assessment.utils.LogConstant.GET_PROJECT_BY_ID;
import static com.teleport.candidate_assessment.utils.TaskManagerConstant.PROJECT_CONTROLLER_CREATE_ENDPOINT_DESCRIPTION;
import static com.teleport.candidate_assessment.utils.TaskManagerConstant.PROJECT_CONTROLLER_CREATE_ENDPOINT_SUMMARY;
import static com.teleport.candidate_assessment.utils.TaskManagerConstant.PROJECT_CONTROLLER_GET_ENDPOINT_DESCRIPTION;
import static com.teleport.candidate_assessment.utils.TaskManagerConstant.PROJECT_CONTROLLER_GET_ENDPOINT_SUMMARY;
import static com.teleport.candidate_assessment.utils.UriConstant.PROJECT_CONTROLLER_GET_ENDPOINT;
import static com.teleport.candidate_assessment.utils.UriConstant.PROJECT_CONTROLLER_URL;

import com.teleport.candidate_assessment.dto.ProjectRequestDTO;
import com.teleport.candidate_assessment.dto.ProjectResponseDTO;
import com.teleport.candidate_assessment.service.ProjectService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

/** The type Project controller. */
@RestController
@RequestMapping(PROJECT_CONTROLLER_URL)
@RequiredArgsConstructor
public class ProjectController {

  private final ProjectService projectService;
  private static final Logger logger = LoggerFactory.getLogger(ProjectController.class);

  /**
   * Create project.
   *
   * @param projectRequestDTO the project request dto
   * @return the project response dto
   */
  @PostMapping
  @Operation(
      summary = PROJECT_CONTROLLER_CREATE_ENDPOINT_SUMMARY,
      description = PROJECT_CONTROLLER_CREATE_ENDPOINT_DESCRIPTION)
  public Mono<ProjectResponseDTO> create(@RequestBody ProjectRequestDTO projectRequestDTO) {
    logger.info(CREATE_PROJECT, projectRequestDTO);
    return projectService.create(projectRequestDTO);
  }

  /**
   * Gets project by id.
   *
   * @param projectId the project id
   * @return the project by id
   */
  @GetMapping(PROJECT_CONTROLLER_GET_ENDPOINT)
  @Operation(
      summary = PROJECT_CONTROLLER_GET_ENDPOINT_SUMMARY,
      description = PROJECT_CONTROLLER_GET_ENDPOINT_DESCRIPTION)
  public Mono<ProjectResponseDTO> getProjectById(@PathVariable String projectId) {
    logger.info(GET_PROJECT_BY_ID, projectId);
    return projectService.getProjectById(projectId);
  }
}
