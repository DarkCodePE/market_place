package com.example.multisecurityspring.domain.service;

import com.example.multisecurityspring.application.dto.LoginDTO;
import com.example.multisecurityspring.application.dto.SignUpDTO;
import com.example.multisecurityspring.domain.entity.*;
import com.example.multisecurityspring.infrastructure.exception.BadRequestException;
import com.example.multisecurityspring.infrastructure.exception.EntityNotFoundException;
import com.example.multisecurityspring.infrastructure.exception.UserLoginException;
import com.example.multisecurityspring.infrastructure.repository.RoleRepository;
import com.example.multisecurityspring.infrastructure.repository.UserRepository;
import com.example.multisecurityspring.infrastructure.security.JwtTokenProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

@Service
@Slf4j(topic = "AUTH_SERVICE")
public class AuthService {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    @Autowired
    private EmailVerificationTokenService emailVerificationTokenService;

    public Optional<User> registerUser(SignUpDTO signUpRequest){
        if (Boolean.TRUE.equals(userRepository.existsByUsername(signUpRequest.getUsername()))){
            throw new EntityNotFoundException("entity.user.already","USERNAME", signUpRequest.getUsername());
        }
        if (Boolean.TRUE.equals(userRepository.existsByEmail(signUpRequest.getEmail().toLowerCase()))){
            throw new EntityNotFoundException("entity.user.already","EMAIL", signUpRequest.getEmail());
        }

        User user = User.builder()
                .firstName(signUpRequest.getFirstName().toLowerCase())
                .lastName(signUpRequest.getLastName().toLowerCase())
                .username(signUpRequest.getUsername().toLowerCase())
                .email(signUpRequest.getEmail().toLowerCase())
                .isEmailVerified(false)
                .password(passwordEncoder.encode(signUpRequest.getPassword()))
                .build();

        Role role = roleRepository.findByName(RoleName.ROLE_USER).orElseThrow(() -> new RuntimeException("User Role not set"));

        UserRole userRole = UserRole.builder()
                .role(role)
                .user(user)
                .build();

        user.setUserRoles(Collections.singleton(userRole));

        return Optional.of(userRepository.save(user));
    }

    public String authenticateUser(LoginDTO loginDTO){
        try{
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    loginDTO.getUsernameOrEmail(),
                    loginDTO.getPassword()
            ));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            return jwtTokenProvider.generateToken(authentication);
        }catch (AuthenticationException e){
            throw new UserLoginException("api.login.fail", e.getMessage().toLowerCase());
        }
    }
    public Optional<Authentication> authentication(LoginDTO loginDTO){
        return Optional.ofNullable(authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginDTO.getUsernameOrEmail(),
                loginDTO.getPassword()
        )));
    }
    /**
     * Confirms the user verification based on the token expiry and mark the user as active.
     * If user is already verified, save the unnecessary database calls.
     */
    public Optional<User> confirmEmailRegistration(String emailToken) {
        EmailVerificationToken emailVerificationToken = emailVerificationTokenService.findByToken(emailToken)
                .orElseThrow(() -> new RuntimeException("Token Email verification"));

        User registeredUser = emailVerificationToken.getUser();
        if (registeredUser.getIsEmailVerified()) {
            log.info("User [" + emailToken + "] already registered.");
            return Optional.of(registeredUser);
        }

        emailVerificationTokenService.verifyExpiration(emailVerificationToken);
        emailVerificationToken.setConfirmedStatus();
        emailVerificationTokenService.save(emailVerificationToken);

        registeredUser.markVerificationConfirmed();
        userRepository.save(registeredUser);
        return Optional.of(registeredUser);
    }

}
