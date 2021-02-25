package com.example.multisecurityspring.application.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderLineDTO {
    private ProductDTO product;
    private float price;
    private int quantity;
}
