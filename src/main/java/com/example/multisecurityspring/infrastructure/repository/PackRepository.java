package com.example.multisecurityspring.infrastructure.repository;

import com.example.multisecurityspring.domain.entity.Pack;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PackRepository extends JpaRepository<Pack, Long> {
    @Query(value = "select l from packs l JOIN FETCH l.products WHERE l.id = :id", nativeQuery = true)
    public Optional<Pack> findByIdInProducts(Long id);
}
