package com.example.multisecurityspring.persistence.entity;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String username;
    private String password;
    private String avatar;
    private String email;
    private String fullName;
    /**
     * @Enumerated indica enumaración se guarda como string
     * @ElementCollection indica el tipo EAGER o LAZY
     */
    @Enumerated(EnumType.STRING)
    @ElementCollection(fetch = FetchType.EAGER)
    private Set<Role> roles;

    @CreatedDate
    private LocalDateTime createAt;

    /**
     * @Builder el valor predeterminado(inicializarlo)
     */
    @Builder.Default
    private LocalDateTime lastPasswordChangeAt = LocalDateTime.now();
}
