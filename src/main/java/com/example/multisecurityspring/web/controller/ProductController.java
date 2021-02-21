package com.example.multisecurityspring.web.controller;

import com.example.multisecurityspring.commons.I18Constants;
import com.example.multisecurityspring.domain.coverter.ProductConverter;
import com.example.multisecurityspring.domain.dto.ProductCreateDto;
import com.example.multisecurityspring.domain.dto.ProductDto;
import com.example.multisecurityspring.domain.exception.NoSuchElementFoundException;
import com.example.multisecurityspring.persistence.entity.Product;
import com.example.multisecurityspring.persistence.repository.ProductRepository;

import com.example.multisecurityspring.utils.CustomMessage;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/products")
@Slf4j(topic = "PRODUCT_CONTROLLER")
public class ProductController {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductConverter productConverter;
    @Autowired
    private CustomMessage customMessage;

    @GetMapping
    public ResponseEntity<List<Object>> getProducts(){
        List<Product> products = productRepository.findAll();
        List<Object> productsDto = productConverter.convertToDto(products, new ProductDto());
        return new ResponseEntity<>(productsDto, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> findById(@PathVariable("id") Long productId){
        Product product = productRepository.findById(productId).orElseThrow(() -> new NoSuchElementFoundException(customMessage.getLocalMessage(I18Constants.NO_ITEM_FOUND.getKey(), productId.toString())));
        ProductDto productDto = (ProductDto) productConverter.convertToDto(product, new ProductDto());
        return new ResponseEntity<>(productDto, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ProductCreateDto> create(@RequestBody @Valid ProductCreateDto request){
        Product product = productRepository.save(productConverter.convertToEntity(request, new Product()));
        ProductCreateDto productCreateDto = (ProductCreateDto) productConverter.convertToDto(product, new ProductCreateDto());
        return new ResponseEntity<>(productCreateDto, HttpStatus.CREATED);
    }
}
