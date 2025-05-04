package com.teleport.candidate_assessment.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/** The type Task Entity */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Task {
  @Id private String id = UUID.randomUUID().toString();

  private String title;

  @ManyToOne(optional = false)
  private User assignee;

  @ManyToOne(optional = false)
  private Project project;

  private String priority;

  private LocalDateTime dueDate;

  private String status;
}
