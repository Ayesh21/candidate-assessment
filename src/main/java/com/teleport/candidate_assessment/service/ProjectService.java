package com.teleport.candidate_assessment.service;

import com.teleport.candidate_assessment.dto.ProjectRequestDTO;
import com.teleport.candidate_assessment.dto.ProjectResponseDTO;

public interface ProjectService {
    ProjectResponseDTO create(ProjectRequestDTO projectRequestDTO);
    ProjectResponseDTO getProjectById(String projectId);
}
