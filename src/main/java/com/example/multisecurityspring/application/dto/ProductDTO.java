package com.example.multisecurityspring.application.dto;

import com.example.multisecurityspring.application.view.ProductView;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductDTO {
    private long id;
    @JsonView(ProductView.External.class)
    private String name;
    @JsonView(ProductView.Internal.class)
    private float price;
    @JsonView(ProductView.External.class)
    private String categoryName;
    @JsonView(ProductView.External.class)
    @JsonIgnoreProperties("products")
    private Set<PackDTO> packs;
}
