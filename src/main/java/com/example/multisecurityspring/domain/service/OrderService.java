package com.example.multisecurityspring.domain.service;

import com.example.multisecurityspring.domain.entity.Order;
import com.example.multisecurityspring.domain.service.base.BaseService;
import com.example.multisecurityspring.infrastructure.repository.OrderRepository;
import org.springframework.stereotype.Service;

@Service
public class OrderService extends BaseService<Order, Long, OrderRepository> {
}
