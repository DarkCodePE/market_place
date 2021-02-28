package com.example.multisecurityspring.infrastructure.repository;

import com.example.multisecurityspring.domain.entity.User;
import com.example.multisecurityspring.infrastructure.exception.NoSuchElementFoundException;
import com.example.multisecurityspring.infrastructure.security.UserPrincipal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.NotBlank;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(@NotBlank String username);
    Optional<User> findByEmail(@NotBlank String email);
    Boolean existsByUsername(@NotBlank String username);
    Boolean existsByEmail(@NotBlank String email);
    Optional<User> findByUsernameOrEmail(String username, String email);
    default User getUser(UserPrincipal currentUser) {
        return getUserByName(currentUser.getUsername());
    }
    default User getUserByName(String username) {
        return findByUsername(username)
                .orElseThrow(() -> new NoSuchElementFoundException(String.format("User not found with this username: %s", username)));
    }
}
