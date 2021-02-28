package com.example.multisecurityspring.domain.service;

import com.example.multisecurityspring.domain.entity.User;
import com.example.multisecurityspring.infrastructure.exception.NoSuchElementFoundException;
import com.example.multisecurityspring.infrastructure.repository.UserRepository;
import com.example.multisecurityspring.infrastructure.security.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    public UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String usernameOrEmail) {
        User user = userRepository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail)
                .orElseThrow(() -> new NoSuchElementFoundException(String.format("User not found with this username or email: %s", usernameOrEmail)));
        return UserPrincipal.create(user);
    }

    @Transactional
    public UserDetails loadUserById(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new NoSuchElementFoundException(String.format("User not found with id: %s", id)));
        return UserPrincipal.create(user);
    }
}
