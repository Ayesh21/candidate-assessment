package com.teleport.candidate_assessment.service;

import com.teleport.candidate_assessment.dto.ProjectRequestDTO;
import com.teleport.candidate_assessment.dto.ProjectResponseDTO;
import reactor.core.publisher.Mono;

/** The interface Project service. */
public interface ProjectService {
  /**
   * Create project response dto.
   *
   * @param projectRequestDTO the project request dto
   * @return the project response dto
   */
  Mono<ProjectResponseDTO> create(ProjectRequestDTO projectRequestDTO);

  /**
   * Gets project by id.
   *
   * @param projectId the project id
   * @return the project by id
   */
  Mono<ProjectResponseDTO> getProjectById(String projectId);
}
