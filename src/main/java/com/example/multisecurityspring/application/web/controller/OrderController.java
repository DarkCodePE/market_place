package com.example.multisecurityspring.application.web.controller;

import com.example.multisecurityspring.application.coverter.OrderConverter;
import com.example.multisecurityspring.application.dto.OrderDto;
import com.example.multisecurityspring.application.dto.PagedResponseDTO;
import com.example.multisecurityspring.domain.entity.Order;
import com.example.multisecurityspring.domain.service.OrderService;
import com.example.multisecurityspring.infrastructure.utils.PaginationLinksUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;

@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private PaginationLinksUtils paginationLinksUtils;
    @Autowired
    private OrderConverter orderConverter;
    @GetMapping
    public ResponseEntity<PagedResponseDTO<Object>> getOrders(
            @RequestParam(value = "pageNumber", required = false, defaultValue = "0") int pageNumber,
            @RequestParam(value = "pageSize", required = false, defaultValue = "5") int pageSize,
            HttpServletRequest request
    ){
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<Order> orders = orderService.findAll(pageable);
        if (orders.hasContent()){
            Page<Object> oderDto = orderConverter.convertToDto(orders, new OrderDto());
            UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder
                    .fromHttpUrl(request.getRequestURL().toString());
            return ResponseEntity.ok()
                    .header("link", paginationLinksUtils.createLinkHeader(oderDto, uriComponentsBuilder))
                    .body(PagedResponseDTO.builder()
                            .total(oderDto.getTotalElements())
                            .pages(oderDto.getTotalPages())
                            .current(oderDto.getNumber())
                            .data(oderDto.toList())
                            .build());
        }else{
            return ResponseEntity.ok().body(PagedResponseDTO.builder()
                    .total(0L)
                    .pages(0)
                    .current(pageNumber)
                    .data(new ArrayList<>())
                    .build());
        }
    }
}
