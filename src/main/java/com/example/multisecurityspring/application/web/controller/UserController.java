package com.example.multisecurityspring.application.web.controller;

import com.example.multisecurityspring.application.coverter.UserConverter;
import com.example.multisecurityspring.application.dto.UserProfileDTO;
import com.example.multisecurityspring.domain.entity.User;
import com.example.multisecurityspring.domain.service.UserService;
import com.example.multisecurityspring.infrastructure.exception.EntityNotFoundException;
import com.example.multisecurityspring.infrastructure.security.CurrentUser;
import com.example.multisecurityspring.infrastructure.security.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private UserConverter userConverter;

    @GetMapping("/me")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<UserProfileDTO> getCurrentUser(@CurrentUser UserPrincipal currentUser) {
        UserProfileDTO userProfileDTO = UserProfileDTO.builder()
                .id(currentUser.getId())
                .username(currentUser.getUsername())
                .firstName(currentUser.getFirstName())
                .lastName(currentUser.getLastName())
                .build();
        return new ResponseEntity<>(userProfileDTO, HttpStatus.OK);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserProfileDTO> findById(@PathVariable("userId") Long userId) {
        User user = userService.findById(userId).orElseThrow(() -> new EntityNotFoundException("entity.user.param", "ID", userId));
        UserProfileDTO userProfileDTO = (UserProfileDTO) userConverter.convertToDto(user, new UserProfileDTO());
        return new ResponseEntity<>(userProfileDTO, HttpStatus.OK);
    }

}
