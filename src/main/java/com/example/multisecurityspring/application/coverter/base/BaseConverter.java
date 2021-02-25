package com.example.multisecurityspring.application.coverter.base;
import com.example.multisecurityspring.domain.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface BaseConverter<E, D> {
    public abstract List<D> convertToDto(List<E> entity, D dto);
    public abstract Page<D> convertToDto(Page<E> entity, D dto);
    public abstract D convertToDto(Product entity, D dto);
    public abstract E convertToEntity(D dto, E entity);
}
