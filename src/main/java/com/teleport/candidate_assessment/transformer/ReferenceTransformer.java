package com.teleport.candidate_assessment.transformer;

import com.teleport.candidate_assessment.dto.ReferenceRequestDTO;
import com.teleport.candidate_assessment.dto.ReferenceResponseDTO;
import com.teleport.candidate_assessment.entity.Reference;
import org.springframework.stereotype.Component;

/** The type Reference transformer. */
@Component
public class ReferenceTransformer {

  /**
   * To entity reference.
   *
   * @param referenceRequestDTO the reference request dto
   * @return the reference
   */
  public Reference toEntity(ReferenceRequestDTO referenceRequestDTO) {
    Reference reference = new Reference();
    reference.setUserId(referenceRequestDTO.userId());
    reference.setProjectId(referenceRequestDTO.projectId());
    reference.setTaskId(referenceRequestDTO.taskId());
    return reference;
  }

  /**
   * To response reference response dto.
   *
   * @param reference the reference
   * @return the reference response dto
   */
  public ReferenceResponseDTO toResponse(Reference reference) {
    return new ReferenceResponseDTO(
        reference.getId(), reference.getUserId(), reference.getProjectId(), reference.getTaskId());
  }
}
