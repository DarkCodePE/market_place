package com.example.multisecurityspring.infrastructure.repository;

import com.example.multisecurityspring.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
