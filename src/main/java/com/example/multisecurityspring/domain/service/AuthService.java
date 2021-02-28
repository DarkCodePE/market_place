package com.example.multisecurityspring.domain.service;

import com.example.multisecurityspring.application.dto.LoginDTO;
import com.example.multisecurityspring.application.dto.SignUpDTO;
import com.example.multisecurityspring.domain.entity.Role;
import com.example.multisecurityspring.domain.entity.RoleName;
import com.example.multisecurityspring.domain.entity.User;
import com.example.multisecurityspring.domain.entity.UserRole;
import com.example.multisecurityspring.infrastructure.exception.BadRequestException;
import com.example.multisecurityspring.infrastructure.exception.EntityNotFoundException;
import com.example.multisecurityspring.infrastructure.exception.UserLoginException;
import com.example.multisecurityspring.infrastructure.repository.RoleRepository;
import com.example.multisecurityspring.infrastructure.repository.UserRepository;
import com.example.multisecurityspring.infrastructure.security.JwtTokenProvider;
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

    public User registerUser(SignUpDTO signUpRequest){
        if (Boolean.TRUE.equals(userRepository.existsByUsername(signUpRequest.getUsername()))){
            throw new EntityNotFoundException("entity.user.param","USERNAME", signUpRequest.getUsername());
        }
        if (Boolean.TRUE.equals(userRepository.existsByEmail(signUpRequest.getEmail()))){
            throw new EntityNotFoundException("entity.user.param","EMAIL", signUpRequest.getEmail());
        }

        User user = User.builder()
                .firstName(signUpRequest.getFirstName().toLowerCase())
                .lastName(signUpRequest.getLastName().toLowerCase())
                .username(signUpRequest.getUsername().toLowerCase())
                .email(signUpRequest.getEmail().toLowerCase())
                .password(passwordEncoder.encode(signUpRequest.getPassword()))
                .build();

        Role role = roleRepository.findByName(RoleName.ROLE_USER).orElseThrow(() -> new RuntimeException("User Role not set"));

        UserRole userRole = UserRole.builder()
                .role(role)
                .user(user)
                .build();

        user.setUserRoles(Collections.singleton(userRole));

        return userRepository.save(user);
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
}
