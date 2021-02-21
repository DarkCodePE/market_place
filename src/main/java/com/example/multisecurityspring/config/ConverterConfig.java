package com.example.multisecurityspring.config;

import com.example.multisecurityspring.domain.coverter.ProductConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConverterConfig {
    @Bean
    public ProductConverter getProductConverter(){
        return new ProductConverter();
    }
}
