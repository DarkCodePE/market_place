package com.example.multisecurityspring.domain.service;

import com.example.multisecurityspring.application.dto.UserProfileDTO;
import com.example.multisecurityspring.domain.entity.User;
import com.example.multisecurityspring.domain.service.base.BaseService;

import com.example.multisecurityspring.infrastructure.repository.UserRepository;
import com.example.multisecurityspring.infrastructure.security.UserPrincipal;

import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService extends BaseService<User, Long, UserRepository> {

    public UserProfileDTO getCurrentUser(UserPrincipal principal){
        return UserProfileDTO.builder()
                .id(principal.getId())
                .username(principal.getUsername())
                .firstName(principal.getFirstName())
                .lastName(principal.getLastName())
                .build();
    }

    public Boolean checkUsernameAvailability(String username){
        return !repository.existsByUsername(username);
    }

    public Boolean checkEmailAvailability(String email){
        return !repository.existsByEmail(email);
    }

    public Optional<User> findByUsername(String username) {
        return repository.findByUsername(username);
    }

    public Optional<User> findByEmail(String email) {
        return repository.findByEmail(email);
    }


}
