package com.example.multisecurityspring.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@Builder
@AllArgsConstructor
/*@EqualsAndHashCode(callSuper = false)*/
@Entity
@Table(name = "users",
        uniqueConstraints = {
            @UniqueConstraint(columnNames = { "username" }),
            @UniqueConstraint(columnNames = { "email" })
        })
public class User extends Auditable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotBlank
    @Column(name = "first_name")
    @Size(max = 40)
    private String firstName;

    @NotBlank
    @Column(name = "last_name")
    @Size(max = 40)
    private String lastName;

    @NotBlank
    @Column(name = "username")
    @Size(max = 15)
    private String username;

    @NotBlank
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Size(max = 100)
    @Column(name = "password")
    private String password;

    @NotBlank
    @NaturalId
    @Size(max = 40)
    @Column(name = "email")
    @Email
    private String email;

    @EqualsAndHashCode.Exclude
    @OneToMany(
            mappedBy = "user",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @JsonIgnoreProperties("user")
    private Set<UserRole> userRoles;

    @Column(name = "is_email_verified", nullable = false)
    private Boolean isEmailVerified;
    /**
     * @Builder el valor predeterminado(inicializarlo)
     */
    @Builder.Default
    private LocalDateTime lastPasswordChangeAt = LocalDateTime.now();

    public User() {
        super();
    }
    public void markVerificationConfirmed() {
        setIsEmailVerified(true);
    }
}
