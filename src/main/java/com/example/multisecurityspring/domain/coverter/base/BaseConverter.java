package com.example.multisecurityspring.domain.coverter.base;
import com.example.multisecurityspring.persistence.entity.Product;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface BaseConverter<E, D> {
    public abstract List<D> convertToDto(List<Product> entity, D dto);
    public abstract D convertToDto(Product entity, D dto);
    public abstract E convertToEntity(D dto, E entity);
}
