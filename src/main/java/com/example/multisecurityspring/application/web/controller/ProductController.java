package com.example.multisecurityspring.application.web.controller;

import com.example.multisecurityspring.application.view.PackView;
import com.example.multisecurityspring.application.view.ProductView;
import com.example.multisecurityspring.application.web.search.CustomRsqlVisitor;
import com.example.multisecurityspring.domain.entity.Pack;
import com.fasterxml.jackson.annotation.JsonView;
import cz.jirutka.rsql.parser.RSQLParser;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.ObjectUtils;
import com.example.multisecurityspring.infrastructure.commons.I18Constants;
import com.example.multisecurityspring.application.coverter.ProductConverter;
import com.example.multisecurityspring.application.dto.PagedResponseDTO;
import com.example.multisecurityspring.application.dto.ProductCreateDTO;
import com.example.multisecurityspring.application.dto.ProductDTO;
import com.example.multisecurityspring.infrastructure.exception.NoSuchElementFoundException;
import com.example.multisecurityspring.domain.entity.Product;
import com.example.multisecurityspring.infrastructure.repository.ProductRepository;

import cz.jirutka.rsql.parser.ast.Node;

import com.example.multisecurityspring.infrastructure.utils.CustomMessage;
import com.example.multisecurityspring.infrastructure.utils.PaginationLinksUtils;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;

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
    @Autowired
    private PaginationLinksUtils paginationLinksUtils;

    @GetMapping
   /* @JsonView({ProductView.Internal.class})*/
    public ResponseEntity<PagedResponseDTO<Object>> getProducts(
            @RequestParam(value = "pageNumber", required = false, defaultValue = "0") int pageNumber,
            @RequestParam(value = "pageSize", required = false, defaultValue = "5") int pageSize,
            @RequestParam(value = "search",required = false) String search,
            HttpServletRequest request
    ){
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<Product> products = null;
        if (ObjectUtils.isEmpty(search)){
            products = productRepository.findAll(pageable);
        }else {
            Node rootNode = new RSQLParser().parse(search);
            Specification<Product> spec = rootNode.accept(new CustomRsqlVisitor<>());
            products = productRepository.findAll(spec, pageable);
        }
        if (products.hasContent()){
            Page<Object> productsDto = productConverter.convertToDto(products, new ProductDTO());
            UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder
                    .fromHttpUrl(request.getRequestURL().toString());
            return ResponseEntity.ok()
                    .header("link", paginationLinksUtils.createLinkHeader(productsDto, uriComponentsBuilder))
                    .body(PagedResponseDTO.builder()
                            .total(productsDto.getTotalElements())
                            .pages(productsDto.getTotalPages())
                            .current(productsDto.getNumber())
                            .data(productsDto.toList())
                            .build());
        }else {
            return ResponseEntity.ok().body(PagedResponseDTO.builder()
                    .total(0L)
                    .pages(0)
                    .current(pageNumber)
                    .data(new ArrayList<>())
                    .build());
        }
    }

    @GetMapping("/{id}")
    @JsonView(ProductView.Internal.class)
    public ResponseEntity<ProductDTO> findById(@PathVariable("id") Long productId){
        Product product = productRepository.findById(productId).orElseThrow(() -> new NoSuchElementFoundException(customMessage.getLocalMessage(I18Constants.NO_ITEM_FOUND.getKey(), productId.toString())));
        ProductDTO productDto = (ProductDTO) productConverter.convertToDto(product, new ProductDTO());
        return new ResponseEntity<>(productDto, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ProductCreateDTO> create(@RequestBody @Valid ProductCreateDTO request){
        Product product = productRepository.save(productConverter.convertToEntity(request, new Product()));
        ProductCreateDTO productCreateDto = (ProductCreateDTO) productConverter.convertToDto(product, new ProductCreateDTO());
        return new ResponseEntity<>(productCreateDto, HttpStatus.CREATED);
    }
}
