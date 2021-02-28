package com.example.multisecurityspring.application.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class LoginDTO {
    @NotBlank
    private String usernameOrEmail;

    @NotBlank
    private String password;
}
