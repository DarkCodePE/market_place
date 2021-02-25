package com.example.multisecurityspring.domain.service;

import com.example.multisecurityspring.domain.service.base.BaseService;

import com.example.multisecurityspring.domain.entity.Product;
import com.example.multisecurityspring.infrastructure.repository.ProductRepository;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j(topic = "PRODUCT_SERVICE")
public class ProductService extends BaseService<Product, Long, ProductRepository> {

    public Optional<Product> findByIdWitchPack(Long id){
        log.info("Product_id:{}", id);
        return repository.findByIdJoinFetch(id);
    }
}
