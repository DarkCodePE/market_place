package com.example.multisecurityspring.persistence.repository;

import com.example.multisecurityspring.persistence.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
