package com.teleport.candidate_assessment.entity;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

/** The type Project Entity */
@Table("project")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Project {
  @Version
  private Long version;

  @Id
  private String id = UUID.randomUUID().toString();

  private String name;

  @Column("owner_id")
  private String ownerId;
}
