//package com.teleport.candidate_assessment.service;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertThrows;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.ArgumentMatchers.eq;
//import static org.mockito.Mockito.when;
//
//import com.teleport.candidate_assessment.dto.TaskRequestDTO;
//import com.teleport.candidate_assessment.dto.TaskResponseDTO;
//import com.teleport.candidate_assessment.entity.Project;
//import com.teleport.candidate_assessment.entity.Task;
//import com.teleport.candidate_assessment.entity.User;
//import com.teleport.candidate_assessment.exception.TaskException;
//import com.teleport.candidate_assessment.exception.TaskNotFoundException;
//import com.teleport.candidate_assessment.repository.ProjectRepository;
//import com.teleport.candidate_assessment.repository.TaskRepository;
//import com.teleport.candidate_assessment.repository.UserRepository;
//import com.teleport.candidate_assessment.service.impl.TaskServiceImpl;
//import java.time.LocalDateTime;
//import java.util.List;
//import java.util.Optional;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageImpl;
//import org.springframework.data.domain.Pageable;
//
///** The type Task service test. */
//@ExtendWith(MockitoExtension.class)
//class TaskServiceTest {
//
//  @Mock private TaskRepository taskRepository;
//  @Mock private ProjectRepository projectRepository;
//  @Mock private UserRepository userRepository;
//
//  @InjectMocks private TaskServiceImpl taskService;
//
//  private TaskRequestDTO taskRequestDTO;
//  private Task sampleTask;
//  private User sampleUser;
//  private Project sampleProject;
//
//  /** Sets up. */
//  @BeforeEach
//  void setUp() {
//    sampleUser = new User();
//    sampleUser.setId("user123");
//    sampleUser.setName("John Doe");
//
//    sampleProject = new Project();
//    sampleProject.setId("proj123");
//    sampleProject.setName("Project X");
//
//    sampleTask = new Task();
//    sampleTask.setId("task123");
//    sampleTask.setTitle("Sample Task");
//    sampleTask.setStatus("NEW");
//    sampleTask.setPriority("HIGH");
//    sampleTask.setDueDate(LocalDateTime.now().plusDays(1));
//    sampleTask.setAssignee(sampleUser);
//    sampleTask.setProject(sampleProject);
//
//    taskRequestDTO =
//        new TaskRequestDTO(
//            "Sample Task", "NEW", "HIGH", "user123", "proj123", sampleTask.getDueDate());
//  }
//
//  /** Create task should return task response. */
//  @Test
//  void createTask_ShouldReturnTaskResponse() {
//    when(projectRepository.findById("proj123")).thenReturn(Optional.of(sampleProject));
//    when(userRepository.findById("user123")).thenReturn(Optional.of(sampleUser));
//    when(taskRepository.save(any(Task.class))).thenReturn(sampleTask);
//
//    TaskResponseDTO result = taskService.createTask(taskRequestDTO);
//
//    assertEquals("task123", result.id());
//    assertEquals("Sample Task", result.title());
//    assertEquals("HIGH", result.priority());
//  }
//
//  /** Gets task by id should return task when found. */
//  @Test
//  void getTaskById_ShouldReturnTask_WhenFound() {
//    when(taskRepository.findById("task123")).thenReturn(Optional.of(sampleTask));
//
//    TaskResponseDTO result = taskService.getTaskById("task123");
//
//    assertEquals("task123", result.id());
//    assertEquals("Sample Task", result.title());
//  }
//
//  /** Gets task by id should throw when not found. */
//  @Test
//  void getTaskById_ShouldThrow_WhenNotFound() {
//    when(taskRepository.findById("invalid")).thenReturn(Optional.empty());
//
//    assertThrows(TaskNotFoundException.class, () -> taskService.getTaskById("invalid"));
//  }
//
//  /** Gets filtered tasks should return paged tasks. */
//  @Test
//  void getFilteredTasks_ShouldReturnPagedTasks() {
//    Page<Task> page = new PageImpl<>(List.of(sampleTask));
//    when(taskRepository.findByProjectIdAndStatusAndPriority(
//            eq("proj123"), eq("NEW"), eq("HIGH"), any(Pageable.class)))
//        .thenReturn(page);
//
//    Page<TaskResponseDTO> result = taskService.getFilteredTasks("proj123", "NEW", "HIGH", 0, 10);
//
//    assertEquals(1, result.getTotalElements());
//    assertEquals("task123", result.getContent().get(0).id());
//  }
//
//  /** Update status should update status when valid transition. */
//  @Test
//  void updateStatus_ShouldUpdateStatus_WhenValidTransition() {
//    sampleTask.setStatus("IN_PROGRESS");
//    when(taskRepository.findById("task123")).thenReturn(Optional.of(sampleTask));
//    when(taskRepository.save(any(Task.class))).thenReturn(sampleTask);
//
//    TaskResponseDTO result = taskService.updateStatus("task123", "COMPLETED");
//
//    assertEquals("COMPLETED", result.status());
//  }
//
//  /** Update status should throw when invalid status. */
//  @Test
//  void updateStatus_ShouldThrow_WhenInvalidStatus() {
//    when(taskRepository.findById("task123")).thenReturn(Optional.of(sampleTask));
//
//    assertThrows(TaskException.class, () -> taskService.updateStatus("task123", "UNKNOWN"));
//  }
//
//  /** Gets user tasks should return tasks assigned to user. */
//  @Test
//  void getUserTasks_ShouldReturnTasksAssignedToUser() {
//    Page<Task> page = new PageImpl<>(List.of(sampleTask));
//    when(taskRepository.findByAssigneeId(eq("user123"), any(Pageable.class))).thenReturn(page);
//
//    Page<TaskResponseDTO> result = taskService.getUserTasks("user123", 0, 10);
//
//    assertEquals(1, result.getTotalElements());
//    assertEquals("task123", result.getContent().get(0).id());
//  }
//
//  /** Gets overdue should return overdue tasks. */
//  @Test
//  void getOverdue_ShouldReturnOverdueTasks() {
//    Page<Task> page = new PageImpl<>(List.of(sampleTask));
//    when(taskRepository.findOverdueTasks(any(LocalDateTime.class), any(Pageable.class)))
//        .thenReturn(page);
//
//    Page<TaskResponseDTO> result = taskService.getOverdue(0, 10);
//
//    assertEquals(1, result.getTotalElements());
//    assertEquals("task123", result.getContent().get(0).id());
//  }
//}
