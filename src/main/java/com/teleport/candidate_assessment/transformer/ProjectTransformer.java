package com.teleport.candidate_assessment.transformer;

import com.teleport.candidate_assessment.dto.ProjectResponseDTO;
import com.teleport.candidate_assessment.entity.Project;
import com.teleport.candidate_assessment.entity.User;

/** The type Project transformer. */
public class ProjectTransformer {
  /**
   * To entity project.
   *
   * @param projectName the project name
   * @param owner the owner
   * @return the project
   */
  public static Project toEntity(final String projectName, final User owner) {
    final Project project = new Project();
    project.setName(projectName);
    project.setOwnerId(owner.getId());
    return project;
  }

  /**
   * To response project response dto.
   *
   * @param project the project
   * @return the project response dto
   */
  public static ProjectResponseDTO toResponse(final Project project) {
    return new ProjectResponseDTO(project.getId(), project.getName(), project.getOwnerId());
  }
}
