package com.example.multisecurityspring.domain.coverter;

import com.example.multisecurityspring.domain.coverter.base.BaseConverter;
import com.example.multisecurityspring.domain.dto.ProductDto;
import com.example.multisecurityspring.persistence.entity.Product;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

public class ProductConverter implements BaseConverter<Product, Object> {
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<Object> convertToDto(List<Product> entity, Object dto) {
        return entity
                .stream()
                .map(element -> modelMapper.map(element, dto.getClass()))
                .collect(Collectors.toList());
    }

    @Override
    public Object convertToDto(Product entity, Object dto) {
        return modelMapper.map(entity, dto.getClass());
    }

    @Override
    public Product convertToEntity(Object dto, Product entity) {
        return modelMapper.map(dto, entity.getClass());
    }
}
