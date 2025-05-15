package com.teleport.candidate_assessment.repository;

import com.teleport.candidate_assessment.entity.Reference;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

/** The interface Reference repository. */
@Repository
public interface ReferenceRepository extends ReactiveCrudRepository<Reference, String> {
  /**
   * Find by user id flux.
   *
   * @param userId the user id
   * @return the flux
   */
  Flux<Reference> findByUserId(String userId);

  /**
   * Find by project id flux.
   *
   * @param projectId the project id
   * @return the flux
   */
  Flux<Reference> findByProjectId(String projectId);

  /**
   * Find by task id flux.
   *
   * @param taskId the task id
   * @return the flux
   */
  Flux<Reference> findByTaskId(String taskId);
}
