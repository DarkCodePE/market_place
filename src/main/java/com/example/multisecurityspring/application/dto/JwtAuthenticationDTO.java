package com.example.multisecurityspring.application.dto;

import lombok.Data;

@Data
public class JwtAuthenticationDTO {
    private String accessToken;
    private String tokenType = "Bearer";
    public JwtAuthenticationDTO(String accessToken) {
        this.accessToken = accessToken;
    }
}
