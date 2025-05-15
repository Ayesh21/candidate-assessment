package com.teleport.candidate_assessment.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.teleport.candidate_assessment.dto.ProjectRequestDTO;
import com.teleport.candidate_assessment.entity.Project;
import com.teleport.candidate_assessment.entity.User;
import com.teleport.candidate_assessment.exception.ProjectNotFoundException;
import com.teleport.candidate_assessment.repository.ProjectRepository;
import com.teleport.candidate_assessment.repository.UserRepository;
import com.teleport.candidate_assessment.service.impl.ProjectServiceImpl;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.redisson.api.RLockReactive;
import org.redisson.api.RedissonReactiveClient;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

/** The type Project service test. */
@ExtendWith(MockitoExtension.class)
class ProjectServiceTest {

  @Mock
  private ProjectRepository projectRepository;
  @Mock
  private UserRepository userRepository;
  @Mock
  private RedissonReactiveClient redissonReactiveClient;
  @Mock
  private RLockReactive rLockReactive;
  @InjectMocks
  private ProjectServiceImpl projectService;

  private ProjectRequestDTO projectRequestDTO;
  private Project project;
  private User user;

  /** Sets up. */
  @BeforeEach
  void setUp() {
    user = new User(null, "user123", "John Doe", "john@example.com");
    projectRequestDTO = new ProjectRequestDTO("Project Alpha", "user123");
    project = new Project(null, "project123", "Project Alpha", "user123");
  }

  /**
   * Create project successfully test.
   *
   * @throws InterruptedException the interrupted exception
   */
  @Test
  void createProjectSuccessfullyTest() throws InterruptedException {
    when(redissonReactiveClient.getLock("owner-lock:user123")).thenReturn(rLockReactive);
    when(rLockReactive.tryLock(10, 30, TimeUnit.SECONDS)).thenReturn(Mono.just(true));
    when(userRepository.findById("user123")).thenReturn(Mono.just(user));
    when(projectRepository.save(any(Project.class))).thenReturn(Mono.just(project));
    doReturn(Mono.just(true)).when(rLockReactive).unlock();

    StepVerifier.create(projectService.create(projectRequestDTO))
        .assertNext(
            result -> {
              assertNotNull(result);
              assertEquals("project123", result.id());
              assertEquals("Project Alpha", result.name());
              assertEquals("user123", result.ownerId());
            })
        .verifyComplete();

    verify(userRepository, times(1)).findById("user123");
    verify(projectRepository, times(1)).save(any(Project.class));
    verify(redissonReactiveClient, times(1)).getLock("owner-lock:user123");
    verify(rLockReactive, times(1)).tryLock(10, 30, TimeUnit.SECONDS);
    verify(rLockReactive, times(1)).unlock();
  }

  /**
   * When owner not found test.
   *
   * @throws InterruptedException the interrupted exception
   */
  @Test
  void whenOwnerNotFoundTest() throws InterruptedException {
    when(redissonReactiveClient.getLock("owner-lock:user123")).thenReturn(rLockReactive);
    when(rLockReactive.tryLock(10, 30, TimeUnit.SECONDS)).thenReturn(Mono.just(true));
    when(userRepository.findById("user123")).thenReturn(Mono.empty());
    doReturn(Mono.just(true)).when(rLockReactive).unlock();

    StepVerifier.create(projectService.create(projectRequestDTO))
        .expectError(ProjectNotFoundException.class)
        .verify();

    verify(userRepository, times(1)).findById("user123");
    verify(projectRepository, never()).save(any(Project.class));
    verify(redissonReactiveClient, times(1)).getLock("owner-lock:user123");
    verify(rLockReactive, times(1)).tryLock(10, 30, TimeUnit.SECONDS);
    verify(rLockReactive, times(1)).unlock();
  }

  /** Find project when id exists test. */
  @Test
  void findProjectWhenIdExistsTest() {
    when(projectRepository.findById("project123")).thenReturn(Mono.just(project));
    // Removed the redundant stubbing for projectRepository

    StepVerifier.create(projectService.getProjectById("project123"))
        .assertNext(
            result -> {
              assertNotNull(result);
              assertEquals("project123", result.id());
              assertEquals("Project Alpha", result.name());
              assertEquals("user123", result.ownerId());
            })
        .verifyComplete();

    verify(projectRepository, times(1)).findById("project123");
    // The userRepository interaction is not expected in this test
  }

  /** When project not found test. */
  @Test
  void whenProjectNotFoundTest() {
    when(projectRepository.findById("invalidId")).thenReturn(Mono.empty());

    StepVerifier.create(projectService.getProjectById("invalidId"))
        .expectError(ProjectNotFoundException.class)
        .verify();

    verify(projectRepository, times(1)).findById("invalidId");
  }
}
