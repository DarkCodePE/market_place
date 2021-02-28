package com.example.multisecurityspring.application.coverter;

import com.example.multisecurityspring.application.coverter.base.BaseConverter;
import com.example.multisecurityspring.domain.entity.User;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

import java.util.List;

public class UserConverter implements BaseConverter<User, Object> {
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<Object> convertToDto(List<User> entity, Object dto) {
        return null;
    }

    @Override
    public Page<Object> convertToDto(Page<User> entity, Object dto) {
        return null;
    }

    @Override
    public Object convertToDto(User entity, Object dto) {
        return modelMapper.map(entity, dto.getClass());
    }

    @Override
    public User convertToEntity(Object dto, User entity) {
        return modelMapper.map(dto, entity.getClass());
    }
}
