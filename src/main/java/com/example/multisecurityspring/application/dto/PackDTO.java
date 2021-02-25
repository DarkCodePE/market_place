package com.example.multisecurityspring.application.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PackDTO {
    private String name;
    @JsonIgnoreProperties("packs")
    private Set<ProductDTO> products;
}
