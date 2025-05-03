package com.teleport.candidate_assessment.repository;

import com.teleport.candidate_assessment.entity.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;

public interface TaskRepository extends JpaRepository<Task, String> {
    Page<Task> findByProjectIdAndStatusAndPriority(String projectId, String status, String priority, Pageable pageable);

    Page<Task> findByAssigneeId(String userId, Pageable pageable);

    @Query("SELECT t FROM Task t WHERE t.dueDate < :today AND t.status != 'COMPLETED'")
    Page<Task> findOverdueTasks(LocalDate today, Pageable pageable);
}
