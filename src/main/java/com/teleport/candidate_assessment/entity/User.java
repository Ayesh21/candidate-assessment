package com.teleport.candidate_assessment.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Version;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/** The type User Entity */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {
  @Version private Long version;

  @Id private String id = UUID.randomUUID().toString();

  @Column(nullable = false)
  private String name;

  @Column(nullable = false, unique = true)
  private String email;
}
