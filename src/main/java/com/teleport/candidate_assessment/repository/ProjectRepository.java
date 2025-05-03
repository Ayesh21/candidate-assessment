package com.teleport.candidate_assessment.repository;

import com.teleport.candidate_assessment.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project, String> {}
