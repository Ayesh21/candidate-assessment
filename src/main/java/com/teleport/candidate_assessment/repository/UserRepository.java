package com.teleport.candidate_assessment.repository;

import com.teleport.candidate_assessment.entity.User;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

/** The interface User repository. */
@Repository
public interface UserRepository extends ReactiveCrudRepository<User, String> {}

