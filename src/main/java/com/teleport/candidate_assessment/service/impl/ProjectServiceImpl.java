package com.teleport.candidate_assessment.service.impl;

import static com.teleport.candidate_assessment.utils.ErrorConstant.COULD_NOT_ACQUIRE_LOCK_FOR_OWNER;

import com.teleport.candidate_assessment.dto.ProjectRequestDTO;
import com.teleport.candidate_assessment.dto.ProjectResponseDTO;
import com.teleport.candidate_assessment.entity.Project;
import com.teleport.candidate_assessment.exception.ProjectNotFoundException;
import com.teleport.candidate_assessment.repository.ProjectRepository;
import com.teleport.candidate_assessment.repository.UserRepository;
import com.teleport.candidate_assessment.service.ProjectService;
import com.teleport.candidate_assessment.transformer.ProjectTransformer;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import org.redisson.api.RLockReactive;
import org.redisson.api.RedissonReactiveClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

/** The type Project service. */
@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {
  private final ProjectRepository projectRepository;
  private final UserRepository userRepository;
  private final RedissonReactiveClient redissonReactiveClient;


  private static final Logger logger = LoggerFactory.getLogger(ProjectServiceImpl.class);

  /**
   * Create project response dto.
   *
   * @param projectRequestDTO the project request dto
   * @return the project response dto
   */
  @Override
  public Mono<ProjectResponseDTO> create(ProjectRequestDTO projectRequestDTO) {
    final String ownerId = projectRequestDTO.ownerId();
    final RLockReactive lock = redissonReactiveClient.getLock("owner-lock:" + ownerId);

    return lock.tryLock(10, 30, TimeUnit.SECONDS)
            .flatMap(acquired -> {
              if (Boolean.TRUE.equals(acquired)) {
                return userRepository.findById(ownerId)
                        .switchIfEmpty(Mono.error(new ProjectNotFoundException(ownerId)))
                        .flatMap(owner -> {
                          Project project = ProjectTransformer.toEntity(projectRequestDTO.name(), owner);
                          return projectRepository.save(project)
                                  .map(ProjectTransformer::toResponse);
                        })
                        .doFinally(signalType -> lock.unlock().subscribe());
              } else {
                return Mono.error(new IllegalStateException(COULD_NOT_ACQUIRE_LOCK_FOR_OWNER + ownerId));
              }
            });
  }


  /**
   * Gets project by id.
   *
   * @param projectId the project id
   * @return the project by id
   */
  @Override
  public Mono<ProjectResponseDTO> getProjectById(String projectId) {
      return projectRepository.findById(projectId)
              .switchIfEmpty(Mono.error(new ProjectNotFoundException(projectId)))
              .map(ProjectResponseDTO::fromEntity);
  }
}
