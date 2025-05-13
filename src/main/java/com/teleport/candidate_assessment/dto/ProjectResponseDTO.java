package com.teleport.candidate_assessment.dto;

import com.teleport.candidate_assessment.entity.Project;

/** The type Project response dto. */
public record ProjectResponseDTO(String id, String name, String ownerId) {

  public static ProjectResponseDTO fromEntity(Project project) {
    return new ProjectResponseDTO(project.getId(), project.getName(), project.getOwnerId());
  }
}
