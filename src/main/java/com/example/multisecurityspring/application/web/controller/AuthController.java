package com.example.multisecurityspring.application.web.controller;

import com.example.multisecurityspring.application.dto.ApiResponseDTO;
import com.example.multisecurityspring.application.dto.JwtAuthenticationDTO;
import com.example.multisecurityspring.application.dto.LoginDTO;
import com.example.multisecurityspring.application.dto.SignUpDTO;
import com.example.multisecurityspring.domain.entity.User;
import com.example.multisecurityspring.domain.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/auth")

public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/signin")
    public ResponseEntity<JwtAuthenticationDTO> authenticateUser(@Valid @RequestBody LoginDTO request){
        String jwt = authService.authenticateUser(request);
        return ResponseEntity.ok(new JwtAuthenticationDTO(jwt));
    }

    @PostMapping("/signup")
    public ResponseEntity<ApiResponseDTO> registerUser(@Valid @RequestBody SignUpDTO request){

        User user = authService.registerUser(request);
        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/api/v1/user/{userId}")
                .buildAndExpand(user.getId()).toUri();

        return ResponseEntity
                .created(location)
                .body(new ApiResponseDTO(Boolean.TRUE, "User registered successfully"));
    }
}
