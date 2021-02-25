package com.example.multisecurityspring.infrastructure.config;

import com.example.multisecurityspring.application.coverter.OrderConverter;
import com.example.multisecurityspring.application.coverter.PackConverter;
import com.example.multisecurityspring.application.coverter.ProductConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConverterConfig {
    @Bean
    public ProductConverter getProductConverter(){
        return new ProductConverter();
    }

    @Bean
    public OrderConverter getOrderConverter(){
        return new OrderConverter();
    }

    @Bean
    public PackConverter getPackConverter(){
        return new PackConverter();
    }
}
