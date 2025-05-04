package com.teleport.candidate_assessment.repository;

import com.teleport.candidate_assessment.entity.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;

/** The interface Task repository. */
public interface TaskRepository extends JpaRepository<Task, String> {
  /**
   * Find by project id and status and priority page.
   *
   * @param projectId the project id
   * @param status the status
   * @param priority the priority
   * @param pageable the pageable
   * @return the page
   */
  Page<Task> findByProjectIdAndStatusAndPriority(
      String projectId, String status, String priority, Pageable pageable);

  /**
   * Find by assignee id page.
   *
   * @param userId the user id
   * @param pageable the pageable
   * @return the page
   */
  Page<Task> findByAssigneeId(String userId, Pageable pageable);

  /**
   * Find overdue tasks page.
   *
   * @param today the today
   * @param pageable the pageable
   * @return the page
   */
  @Query("SELECT t FROM Task t WHERE t.dueDate < :today AND t.status != 'COMPLETED'")
  Page<Task> findOverdueTasks(LocalDate today, Pageable pageable);
}
