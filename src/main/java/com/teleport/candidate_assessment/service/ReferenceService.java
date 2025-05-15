package com.teleport.candidate_assessment.service;

import com.teleport.candidate_assessment.dto.ReferenceRequestDTO;
import com.teleport.candidate_assessment.dto.ReferenceResponseDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/** The interface Reference service. */
public interface ReferenceService {

  /**
   * Create reference mono.
   *
   * @param dto the dto
   * @return the mono
   */
  Mono<ReferenceResponseDTO> createReference(ReferenceRequestDTO dto);

  /**
   * Gets by user id.
   *
   * @param userId the user id
   * @return the by user id
   */
  Flux<ReferenceResponseDTO> getByUserId(String userId);

  /**
   * Gets by project id.
   *
   * @param projectId the project id
   * @return the by project id
   */
  Flux<ReferenceResponseDTO> getByProjectId(String projectId);

  /**
   * Gets by task id.
   *
   * @param taskId the task id
   * @return the by task id
   */
  Flux<ReferenceResponseDTO> getByTaskId(String taskId);
}
