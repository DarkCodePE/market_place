package com.example.multisecurityspring.domain.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "users")
public class User extends Auditable{
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

    @OneToMany(mappedBy = "user")
    private Set<UserRole> userRoles;

    /**
     * @Builder el valor predeterminado(inicializarlo)
     */
    @Builder.Default
    private LocalDateTime lastPasswordChangeAt = LocalDateTime.now();
}
