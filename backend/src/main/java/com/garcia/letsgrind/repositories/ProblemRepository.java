package com.garcia.letsgrind.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.garcia.letsgrind.domain.problem.Problem;

import java.util.UUID;

public interface ProblemRepository extends JpaRepository<Problem, UUID> {
}