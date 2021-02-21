package com.example.multisecurityspring.domain.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ProductDto {
    private long id;
    private String name;
    private String categoryName;
}
