package com.teleport.candidate_assessment.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    private String id = UUID.randomUUID().toString();

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

}
