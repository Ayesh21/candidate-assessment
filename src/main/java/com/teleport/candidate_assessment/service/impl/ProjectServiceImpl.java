package com.teleport.candidate_assessment.service.impl;

import com.teleport.candidate_assessment.dto.ProjectRequestDTO;
import com.teleport.candidate_assessment.dto.ProjectResponseDTO;
import com.teleport.candidate_assessment.entity.Project;
import com.teleport.candidate_assessment.entity.User;
import com.teleport.candidate_assessment.exception.ProjectNotFoundException;
import com.teleport.candidate_assessment.repository.ProjectRepository;
import com.teleport.candidate_assessment.repository.UserRepository;
import com.teleport.candidate_assessment.service.ProjectService;
import com.teleport.candidate_assessment.transformer.ProjectTransformer;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {
  private final ProjectRepository projectRepository;
  private final UserRepository userRepository;

  /**
   *
   * @param projectRequestDTO
   * @return
   */
  @Override
  public ProjectResponseDTO create(ProjectRequestDTO projectRequestDTO) {
    User owner = userRepository.findById(projectRequestDTO.ownerId()).orElseThrow();
    Project project = ProjectTransformer.toEntity(projectRequestDTO.name(), owner);
    return ProjectTransformer.toResponse(projectRepository.save(project));
  }

  @Override
  @Cacheable(value = "projectData", key = "#projectId")
  public ProjectResponseDTO getProjectById(String projectId) {
    return ProjectResponseDTO.fromEntity(
        projectRepository
            .findById(projectId)
            .orElseThrow(() -> new ProjectNotFoundException(projectId)));
  }

}
