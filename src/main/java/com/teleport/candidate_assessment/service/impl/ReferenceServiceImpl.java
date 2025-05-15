package com.teleport.candidate_assessment.service.impl;

import com.teleport.candidate_assessment.dto.ReferenceRequestDTO;
import com.teleport.candidate_assessment.dto.ReferenceResponseDTO;
import com.teleport.candidate_assessment.entity.Reference;
import com.teleport.candidate_assessment.repository.ReferenceRepository;
import com.teleport.candidate_assessment.service.ReferenceService;
import com.teleport.candidate_assessment.transformer.ReferenceTransformer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/** The type Reference service. */
@Service
@RequiredArgsConstructor
public class ReferenceServiceImpl implements ReferenceService {

  private final ReferenceRepository repository;
  private final ReferenceTransformer transformer;

  /**
   * Create reference mono.
   *
   * @param dto the dto
   * @return the mono
   */
  public Mono<ReferenceResponseDTO> createReference(ReferenceRequestDTO dto) {
    Reference reference = transformer.toEntity(dto);
    return repository.save(reference).map(transformer::toResponse);
  }

  /**
   * Gets by user id.
   *
   * @param userId the user id
   * @return the by user id
   */
  public Flux<ReferenceResponseDTO> getByUserId(String userId) {
    return repository.findByUserId(userId).map(transformer::toResponse);
  }

  /**
   * Gets by project id.
   *
   * @param projectId the project id
   * @return the by project id
   */
  public Flux<ReferenceResponseDTO> getByProjectId(String projectId) {
    return repository.findByProjectId(projectId).map(transformer::toResponse);
  }

  /**
   * Gets by task id.
   *
   * @param taskId the task id
   * @return the by task id
   */
  public Flux<ReferenceResponseDTO> getByTaskId(String taskId) {
    return repository.findByTaskId(taskId).map(transformer::toResponse);
  }
}
