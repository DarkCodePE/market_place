package com.example.multisecurityspring.application.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class ProductCreateDTO {
    @NotNull
    @NotBlank
    private String name;
    @Min(value = 1, message = "Price should not be less than 1")
    @NotNull
    private float price;
    private long categoryId;
}
