package com.example.multisecurityspring.application.dto;

import com.example.multisecurityspring.application.view.ProductView;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class PagedResponseDTO<T> {
    @JsonView(ProductView.Internal.class)
    private Long total;
    @JsonView(ProductView.Internal.class)
    private Integer pages;
    @JsonView(ProductView.Internal.class)
    private Integer current;
    @JsonView(ProductView.External.class)
    private T data;
}
