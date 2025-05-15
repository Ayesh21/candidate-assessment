package com.teleport.candidate_assessment.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.teleport.candidate_assessment.dto.TaskRequestDTO;
import com.teleport.candidate_assessment.entity.Project;
import com.teleport.candidate_assessment.entity.Task;
import com.teleport.candidate_assessment.entity.User;
import com.teleport.candidate_assessment.exception.TaskException;
import com.teleport.candidate_assessment.exception.TaskNotFoundException;
import com.teleport.candidate_assessment.repository.ProjectRepository;
import com.teleport.candidate_assessment.repository.TaskRepository;
import com.teleport.candidate_assessment.repository.UserRepository;
import com.teleport.candidate_assessment.service.impl.TaskServiceImpl;
import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

/** The type Task service test. */
@ExtendWith(MockitoExtension.class)
class TaskServiceTest {

  @Mock
  private TaskRepository taskRepository;
  @Mock
  private ProjectRepository projectRepository;
  @Mock
  private UserRepository userRepository;
  @InjectMocks
  private TaskServiceImpl taskService;

  private Task sampleTask;
  private User sampleUser;
  private Project sampleProject;
  private TaskRequestDTO taskRequestDTO;

  /** Sets up. */
  @BeforeEach
  void setUp() {

    sampleUser = new User();
    sampleUser.setId("user123");
    sampleUser.setName("John Doe");

    sampleProject = new Project();
    sampleProject.setId("proj123");
    sampleProject.setName("Project X");

    sampleTask = new Task();
    sampleTask.setId("task123");
    sampleTask.setTitle("Sample Task");
    sampleTask.setStatus("NEW");
    sampleTask.setPriority("HIGH");
    sampleTask.setDueDate(LocalDateTime.now().plusDays(1));
    sampleTask.setAssigneeId(sampleUser.getId());
    sampleTask.setProjectId(sampleProject.getId());
    sampleTask.setAssigneeId("user123");
    sampleTask.setProjectId("proj123");

    taskRequestDTO =
        new TaskRequestDTO(
            "Sample Task", "NEW", "HIGH", "user123", "proj123", sampleTask.getDueDate());
  }

  /** Create task should return task response. */
  @Test
  void createTask_ShouldReturnTaskResponse() {
    when(projectRepository.findById("proj123")).thenReturn(Mono.just(sampleProject));
    when(userRepository.findById("user123")).thenReturn(Mono.just(sampleUser));
    when(taskRepository.createTask(any(), any(), any(), any(), any(), any(), any()))
        .thenReturn(Mono.empty());

    StepVerifier.create(taskService.createTask(taskRequestDTO))
        .expectNextMatches(
            resp -> resp.title().equals("Sample Task") && resp.priority().equals("HIGH"))
        .verifyComplete();
  }

  /** Gets task by id should return task response. */
  @Test
  void getTaskById_ShouldReturnTaskResponse() {
    when(taskRepository.findById("task123")).thenReturn(Mono.just(sampleTask));

    StepVerifier.create(taskService.getTaskById("task123"))
        .expectNextMatches(resp -> resp.id().equals("task123"))
        .verifyComplete();
  }

  /** Gets task by id should throw not found. */
  @Test
  void getTaskById_ShouldThrowNotFound() {
    when(taskRepository.findById("invalid")).thenReturn(Mono.empty());

    StepVerifier.create(taskService.getTaskById("invalid"))
        .expectError(TaskNotFoundException.class)
        .verify();
  }

  /** Gets filtered tasks should return list. */
  @Test
  void getFilteredTasks_ShouldReturnList() {
    when(taskRepository.findByProjectIdAndStatusAndPriority("proj123", "NEW", "HIGH"))
        .thenReturn(Flux.just(sampleTask));

    StepVerifier.create(taskService.getFilteredTasks("proj123", "NEW", "HIGH", 0, 10))
        .expectNextMatches(resp -> resp.id().equals("task123"))
        .verifyComplete();
  }

  /** Update status should update. */
  @Test
  void updateStatus_ShouldUpdate() {
    sampleTask.setStatus("IN_PROGRESS");
    when(taskRepository.findById("task123")).thenReturn(Mono.just(sampleTask));
    when(taskRepository.save(any(Task.class))).thenReturn(Mono.just(sampleTask));

    StepVerifier.create(taskService.updateStatus("task123", "COMPLETED"))
        .expectNextMatches(resp -> resp.status().equals("COMPLETED"))
        .verifyComplete();
  }

  /** Update status should throw on invalid status. */
  @Test
  void updateStatus_ShouldThrowOnInvalidStatus() {
    when(taskRepository.findById("task123")).thenReturn(Mono.just(sampleTask));

    StepVerifier.create(taskService.updateStatus("task123", "UNKNOWN"))
        .expectError(TaskException.class)
        .verify();
  }

  /** Gets user tasks should return list. */
  @Test
  void getUserTasks_ShouldReturnList() {
    when(taskRepository.findByAssigneeId("user123")).thenReturn(Flux.just(sampleTask));

    StepVerifier.create(taskService.getUserTasks("user123", 0, 10))
        .expectNextMatches(resp -> resp.id().equals("task123"))
        .verifyComplete();
  }

  /** Gets overdue should return list. */
  @Test
  void getOverdue_ShouldReturnList() {
    when(taskRepository.findOverdueTasks(any())).thenReturn(Flux.just(sampleTask));

    StepVerifier.create(taskService.getOverdue(0, 10))
        .expectNextMatches(resp -> resp.id().equals("task123"))
        .verifyComplete();
  }
}
