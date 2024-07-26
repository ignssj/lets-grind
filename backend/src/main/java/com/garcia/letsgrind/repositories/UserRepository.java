package com.garcia.letsgrind.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.garcia.letsgrind.domain.user.User;

public interface UserRepository extends JpaRepository<User, UUID> {
}