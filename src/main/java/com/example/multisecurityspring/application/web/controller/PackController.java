package com.example.multisecurityspring.application.web.controller;

import com.example.multisecurityspring.application.coverter.PackConverter;
import com.example.multisecurityspring.application.dto.CreatePackDTO;
import com.example.multisecurityspring.application.dto.PackDTO;
import com.example.multisecurityspring.application.dto.PagedResponseDTO;
import com.example.multisecurityspring.application.view.PackView;
import com.example.multisecurityspring.application.view.ProductView;
import com.example.multisecurityspring.domain.entity.Pack;

import com.example.multisecurityspring.domain.service.PackService;
import com.example.multisecurityspring.infrastructure.utils.PaginationLinksUtils;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;


import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;

@RestController
@RequestMapping("/pack")
@Slf4j(topic = "PACK_CONTROLLER")
public class PackController {

    @Autowired
    private PackService packService;
    @Autowired
    private PaginationLinksUtils paginationLinksUtils;
    @Autowired
    private PackConverter packConverter;

    @GetMapping
    /*@JsonView(ProductView.Internal.class)*/
    public ResponseEntity<Object> getPack(
            @RequestParam(value = "pageNumber", required = false, defaultValue = "0") int pageNumber,
            @RequestParam(value = "pageSize", required = false, defaultValue = "5") int pageSize,
            HttpServletRequest request
    ){
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<Pack> packs = packService.findAll(pageable);
        log.info("data_pack:{}", packs.toList());
        if (packs.hasContent()){
            Page<Object> packDTO = packConverter.convertToDto(packs, new PackDTO());
            UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder
                    .fromHttpUrl(request.getRequestURL().toString());
            return ResponseEntity.ok()
                    .header("link", paginationLinksUtils.createLinkHeader(packDTO, uriComponentsBuilder))
                    .body(PagedResponseDTO.builder()
                            .total(packDTO.getTotalElements())
                            .pages(packDTO.getTotalPages())
                            .current(packDTO.getNumber())
                            .data(packDTO.toList())
                            .build());
        }else {
            return ResponseEntity.ok().body(PagedResponseDTO.builder()
                    .total(0L)
                    .pages(0)
                    .current(pageNumber)
                    .data(new ArrayList<>())
                    .build());
        }
    }

    @PostMapping
    public ResponseEntity<Pack> createPack(@RequestBody CreatePackDTO packDTO){
        Pack pack = packService.saveProductInPack(packDTO);
        return new ResponseEntity<>(pack, HttpStatus.OK);
    }
}
