package com.example.multisecurityspring.domain.service;

import com.example.multisecurityspring.domain.service.base.BaseService;

import com.example.multisecurityspring.persistence.entity.Product;
import com.example.multisecurityspring.persistence.repository.ProductRepository;
import org.springframework.stereotype.Service;

@Service
public class ProductService extends BaseService<Product, Long, ProductRepository> {

}
