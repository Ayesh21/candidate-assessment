package com.teleport.candidate_assessment.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.teleport.candidate_assessment.dto.ProjectRequestDTO;
import com.teleport.candidate_assessment.dto.ProjectResponseDTO;
import com.teleport.candidate_assessment.entity.Project;
import com.teleport.candidate_assessment.entity.User;
import com.teleport.candidate_assessment.exception.ProjectNotFoundException;
import com.teleport.candidate_assessment.repository.ProjectRepository;
import com.teleport.candidate_assessment.repository.UserRepository;
import com.teleport.candidate_assessment.service.impl.ProjectServiceImpl;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;

/** The type Project service test. */
@ExtendWith(MockitoExtension.class)
class ProjectServiceTest {

  @Mock private ProjectRepository projectRepository;
  @Mock private UserRepository userRepository;
  @Mock
  private RedissonClient redissonClient;
  @Mock
  private RLock rLock;
  @InjectMocks private ProjectServiceImpl projectService;
  private ProjectRequestDTO projectRequestDTO;
  private Project project;
  private User user;

  /** Sets up. */
  @BeforeEach
  void setUp() {
    user = new User(1L,"user123", "John Doe", "john@example.com");
    projectRequestDTO = new ProjectRequestDTO("Project Alpha", "user123");
    project = new Project(1L,"project123", "Project Alpha", user);
  }

  /** Create project successfully test. */
  @Test
  void createProjectSuccessfullyTest() throws InterruptedException {
    when(redissonClient.getLock("owner-lock:user123")).thenReturn(rLock);
    when(rLock.tryLock(10, 30, TimeUnit.SECONDS)).thenReturn(true);
    doNothing().when(rLock).lock();
    doNothing().when(rLock).unlock();

    when(userRepository.findById("user123")).thenReturn(Optional.of(user));
    when(projectRepository.save(any(Project.class))).thenReturn(project);

    ProjectResponseDTO result = projectService.create(projectRequestDTO);

    assertNotNull(result);
    assertEquals("project123", result.id());
    assertEquals("Project Alpha", result.name());
    assertEquals("user123", result.ownerId());

    verify(userRepository, times(1)).findById("user123");
    verify(projectRepository, times(1)).save(any(Project.class));
    verify(rLock).unlock();
  }

  /** When owner not found test. */
  @Test
  void whenOwnerNotFoundTest() throws InterruptedException {
    when(redissonClient.getLock("owner-lock:user123")).thenReturn(rLock);
    when(rLock.tryLock(10, 30, TimeUnit.SECONDS)).thenReturn(true);
    doNothing().when(rLock).lock();
    doNothing().when(rLock).unlock();

    when(userRepository.findById("user123")).thenReturn(Optional.empty());

    assertThrows(NoSuchElementException.class, () -> projectService.create(projectRequestDTO));

    verify(userRepository, times(1)).findById("user123");
    verify(projectRepository, never()).save(any(Project.class));
    verify(rLock).unlock();
  }

  /** Find project when id exists test. */
  @Test
  void findProjectWhenIdExistsTest() {
    when(projectRepository.findById("project123")).thenReturn(Optional.of(project));

    ProjectResponseDTO result = projectService.getProjectById("project123");

    assertNotNull(result);
    assertEquals("project123", result.id());
    assertEquals("Project Alpha", result.name());
    assertEquals("user123", result.ownerId());

    verify(projectRepository, times(1)).findById("project123");
  }

  /** When project not found test. */
  @Test
  void whenProjectNotFoundTest() {
    when(projectRepository.findById("invalidId")).thenReturn(Optional.empty());

    assertThrows(ProjectNotFoundException.class, () -> projectService.getProjectById("invalidId"));

    verify(projectRepository, times(1)).findById("invalidId");
  }
}
