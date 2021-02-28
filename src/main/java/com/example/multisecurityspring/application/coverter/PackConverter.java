package com.example.multisecurityspring.application.coverter;

import com.example.multisecurityspring.application.coverter.base.BaseConverter;
import com.example.multisecurityspring.domain.entity.Pack;
import com.example.multisecurityspring.domain.entity.Product;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;

public class PackConverter implements BaseConverter<Pack, Object> {
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<Object> convertToDto(List<Pack> entity, Object dto) {
        return entity
                .stream()
                .map(element -> modelMapper.map(element, dto.getClass()))
                .collect(Collectors.toList());
    }

    @Override
    public Page<Object> convertToDto(Page<Pack> entity, Object dto) {
        return entity.map(element -> modelMapper.map(element, dto.getClass()));
    }


    @Override
    public Object convertToDto(Pack entity, Object dto) {
        return modelMapper.map(entity, dto.getClass());
    }

    @Override
    public Pack convertToEntity(Object dto, Pack entity) {
        return modelMapper.map(dto, entity.getClass());
    }
}
