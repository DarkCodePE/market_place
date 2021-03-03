package com.example.multisecurityspring.application.web.controller;

import com.example.multisecurityspring.application.dto.ApiResponseDTO;
import com.example.multisecurityspring.application.dto.JwtAuthenticationDTO;
import com.example.multisecurityspring.application.dto.LoginDTO;
import com.example.multisecurityspring.application.dto.SignUpDTO;
import com.example.multisecurityspring.domain.entity.User;
import com.example.multisecurityspring.domain.service.AuthService;
import com.example.multisecurityspring.infrastructure.event.OnUserRegistrationCompleteEvent;
import com.example.multisecurityspring.infrastructure.exception.UserRegistrationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/auth")
@Slf4j(topic = "AUTH_CONTROLLER")
public class AuthController {

    @Autowired
    private AuthService authService;
    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    @PostMapping("/signin")
    public ResponseEntity<JwtAuthenticationDTO> authenticateUser(@Valid @RequestBody LoginDTO request){
        String jwt = authService.authenticateUser(request);
        return ResponseEntity.ok(new JwtAuthenticationDTO(jwt));
    }

    @PostMapping("/signup")
    public ResponseEntity<ApiResponseDTO> registerUser(@Valid @RequestBody SignUpDTO request){
        return authService.registerUser(request).map(user -> {
            UriComponentsBuilder uriComponentsBuilder = ServletUriComponentsBuilder.fromCurrentContextPath().path("/auth/registrationConfirmation");
            log.info("user: {}", user.getEmail());
            OnUserRegistrationCompleteEvent onUserRegistrationCompleteEvent = new OnUserRegistrationCompleteEvent(user, uriComponentsBuilder);
            log.info("onUserRegistrationCompleteEvent:{}", onUserRegistrationCompleteEvent.getUser().getEmail());
            applicationEventPublisher.publishEvent(onUserRegistrationCompleteEvent);
            log.info("Registered User returned [API[: " + user);
            return ResponseEntity.ok(new ApiResponseDTO(Boolean.TRUE, "User registered successfully. Check your email for verification"));
        }).orElseThrow(() -> new UserRegistrationException("api.user.register", request.getEmail(), "Missing user object in database"));
    }

    /**
     * Confirm the email verification token generated for the user during
     * registration. If token is invalid or token is expired, report error.
     */
    @GetMapping("/registrationConfirmation")
    public ResponseEntity<ApiResponseDTO> confirmRegistration(@RequestParam("token") String token) {

        return authService.confirmEmailRegistration(token)
                .map(user -> ResponseEntity.ok(new ApiResponseDTO(true, "User verified successfully")))
                .orElseThrow(() -> new RuntimeException("Email Verification Token Failed to confirm. Please generate a new email verification request"));
    }
}
