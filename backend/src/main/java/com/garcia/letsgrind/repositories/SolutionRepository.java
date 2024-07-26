package com.garcia.letsgrind.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.garcia.letsgrind.domain.solution.Solution;

public interface SolutionRepository extends JpaRepository<Solution, UUID> {
}