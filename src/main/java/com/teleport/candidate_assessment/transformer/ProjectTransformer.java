package com.teleport.candidate_assessment.transformer;

import com.teleport.candidate_assessment.dto.ProjectResponseDTO;
import com.teleport.candidate_assessment.entity.Project;
import com.teleport.candidate_assessment.entity.User;

public class ProjectTransformer {
    public static Project toEntity(String projectName, User owner) {
        Project project = new Project();
        project.setName(projectName);
        project.setOwner(owner);
        return project;
    }

    public static ProjectResponseDTO toResponse(Project project) {
        return new ProjectResponseDTO(
                project.getId(),
                project.getName(),
                project.getOwner().getId()
        );
    }
}
