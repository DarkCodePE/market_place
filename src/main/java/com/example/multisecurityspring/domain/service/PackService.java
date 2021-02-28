package com.example.multisecurityspring.domain.service;

import com.example.multisecurityspring.application.dto.CreatePackDTO;
import com.example.multisecurityspring.domain.entity.Pack;
import com.example.multisecurityspring.domain.service.base.BaseService;

import com.example.multisecurityspring.infrastructure.repository.PackRepository;
import com.example.multisecurityspring.infrastructure.utils.CustomMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;


@Service
@Slf4j(topic = "PACK_SERVICE")
public class PackService extends BaseService<Pack, Long, PackRepository> {
    @Autowired
    private ProductService productService;
    @Autowired
    private CustomMessage customMessage;

    @Override
    public Optional<Pack> findById(Long aLong) {
        return repository.findByIdInProducts(aLong);
    }
    @Transactional
    public Pack saveProductInPack(CreatePackDTO packDTO){

        Pack pack = Pack.builder()
                .name(packDTO.getName())
                .build();
        log.info("getId:{}",   packDTO.getProducts().toString());

        for (Long id: packDTO.getProducts()){
            pack.addProducts(productService.findByIdWitchPack(id).orElseThrow(() -> new RuntimeException("Product does not exist")));
        }
        log.info("pack:{}", pack);
        return save(pack);
    }
}
