package com.example.multisecurityspring.persistence.repository;

import com.example.multisecurityspring.persistence.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {

}
