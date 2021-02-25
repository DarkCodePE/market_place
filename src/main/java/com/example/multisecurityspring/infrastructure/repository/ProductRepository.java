package com.example.multisecurityspring.infrastructure.repository;

import com.example.multisecurityspring.domain.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {
    @Query(value = "select p from Product p LEFT JOIN FETCH p.packs WHERE p.id = :id")
    public Optional<Product> findByIdJoinFetch(Long id);
}
