package com.teleport.candidate_assessment.dto;

import com.teleport.candidate_assessment.entity.Reference;

/** The type Reference response dto. */
public record ReferenceResponseDTO(String id, String userId, String projectId, String taskId) {

  public static ReferenceResponseDTO fromEntity(Reference reference) {
    return new ReferenceResponseDTO(
        reference.getId(), reference.getUserId(), reference.getProjectId(), reference.getTaskId());
  }
}
