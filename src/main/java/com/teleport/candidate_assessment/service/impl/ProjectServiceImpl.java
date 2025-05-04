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
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/** The type Project service. */
@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {
  private final ProjectRepository projectRepository;
  private final UserRepository userRepository;
  private final RedissonClient redissonClient;

  private static final Logger logger = LoggerFactory.getLogger(ProjectServiceImpl.class);

  /**
   * Create project response dto.
   *
   * @param projectRequestDTO the project request dto
   * @return the project response dto
   */
  @Transactional
  @Override
  public ProjectResponseDTO create(final ProjectRequestDTO projectRequestDTO)
      throws InterruptedException {
    final String ownerId = projectRequestDTO.ownerId();
    RLock lock = redissonClient.getLock("owner-lock:" + ownerId);
    if (lock.tryLock(10, 30, TimeUnit.SECONDS)) {
      try {
        lock.lock();
        final User owner = userRepository.findById(ownerId).orElseThrow();
        final Project project = ProjectTransformer.toEntity(projectRequestDTO.name(), owner);
        return ProjectTransformer.toResponse(projectRepository.save(project));
      } finally {
        lock.unlock();
      }
    } else {
      throw new IllegalStateException("Could not acquire lock for ownerId: " + ownerId);
    }
  }

  /**
   * Gets project by id.
   *
   * @param projectId the project id
   * @return the project by id
   */
  @Override
  @Cacheable(value = "projectData", key = "#projectId")
  public ProjectResponseDTO getProjectById(final String projectId) {
    return ProjectResponseDTO.fromEntity(
        projectRepository
            .findById(projectId)
            .orElseThrow(() -> new ProjectNotFoundException(projectId)));
  }
}
