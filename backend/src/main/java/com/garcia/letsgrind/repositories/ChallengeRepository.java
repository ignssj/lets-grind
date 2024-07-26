package com.garcia.letsgrind.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.garcia.letsgrind.domain.challenge.Challenge;

import java.util.UUID;

public interface ChallengeRepository extends JpaRepository<Challenge, UUID> {
}