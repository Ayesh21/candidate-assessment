package com.teleport.candidate_assessment.repository;

import com.teleport.candidate_assessment.entity.Task;
import java.time.LocalDateTime;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/** The interface Task repository. */
@Repository
public interface TaskRepository extends ReactiveCrudRepository<Task, String> {

  /**
   * Insert task mono.
   *
   * @param id the id
   * @param title the title
   * @param assigneeId the assignee id
   * @param projectId the project id
   * @param priority the priority
   * @param dueDate the due date
   * @param status the status
   * @return the mono
   */
  @Query(
      """
        INSERT INTO task (id, title, assignee_id, project_id, priority, due_date, status)
        VALUES (:id, :title, :assigneeId, :projectId, :priority, :dueDate, :status)
        """)
  Mono<Void> createTask(
      String id,
      String title,
      String assigneeId,
      String projectId,
      String priority,
      LocalDateTime dueDate,
      String status);

  /**
   * Find by project id and status and priority page.
   *
   * @param projectId the project id
   * @param status the status
   * @param priority the priority
   * @return the page
   */
  Flux<Task> findByProjectIdAndStatusAndPriority(String projectId, String status, String priority);

  /**
   * Find by assignee id page.
   *
   * @param userId the user id
   * @return the page
   */
  Flux<Task> findByAssigneeId(String userId);

  /**
   * Find overdue tasks page.
   *
   * @param today the today
   * @return the page
   */
  @Query("SELECT * FROM task WHERE due_date < :today AND status != 'COMPLETED'")
  Flux<Task> findOverdueTasks(LocalDateTime today);
}
