package com.example.multisecurityspring.application.dto;

import lombok.*;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderDto {
    private String name;
    private Set<OrderLineDTO> lines;
}
