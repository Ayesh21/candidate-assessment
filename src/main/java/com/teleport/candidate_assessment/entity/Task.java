package com.teleport.candidate_assessment.entity;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

/** The type Task Entity */
@Table("task")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Task {
  @Id
  private String id;

  private String title;

  @Column("assignee_id")
  private String assigneeId;

  @Column("project_id")
  private String projectId;

  private String priority;

  private LocalDateTime dueDate;

  private String status;
}
