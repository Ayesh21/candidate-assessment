package com.teleport.candidate_assessment.repository;

import com.teleport.candidate_assessment.entity.Project;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

/** The interface Project repository. */
@Repository
public interface ProjectRepository extends ReactiveCrudRepository<Project, String> {
}


