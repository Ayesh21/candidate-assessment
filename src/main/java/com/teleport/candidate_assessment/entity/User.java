package com.teleport.candidate_assessment.entity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

/** The type User Entity */
@Table("user")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {
  @Version
  private Long version;

  @Id
  private String id;

  @Column
  private String name;

  @Column
  private String email;
}
