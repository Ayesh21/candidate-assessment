package com.teleport.candidate_assessment.controller;

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

/** The type Project controller. */
@RestController
@RequestMapping("api/projects")
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
      summary = "Create a new project",
      description = "Creates a new project with a name and owner ID")
  public ProjectResponseDTO create(@RequestBody final ProjectRequestDTO projectRequestDTO)
      throws InterruptedException {
    logger.info("Creating Project: {}", projectRequestDTO);
    return projectService.create(projectRequestDTO);
  }

  /**
   * Gets project by id.
   *
   * @param projectId the project id
   * @return the project by id
   */
  @GetMapping("/{projectId}")
  @Operation(
      summary = "Get project by ID",
      description = "Fetches project details by its unique ID")
  public ProjectResponseDTO getProjectById(@PathVariable final String projectId) {
    logger.info("Fetching Project by project ID: {}", projectId);
    return projectService.getProjectById(projectId);
  }
}
