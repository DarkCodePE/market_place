package com.example.multisecurityspring.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.Instant;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class EmailVerificationToken {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "email_token_seq")
    @SequenceGenerator(name = "email_token_seq", allocationSize = 1)
    private Long id;

    @Column(name = "token", nullable = false, unique = true)
    private String token;
    @OneToOne(targetEntity = User.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "id")
    private User user;

    @Column(name = "token_status")
    @Enumerated(EnumType.STRING)
    private TokenStatus tokenStatus;

    @Column(name = "expiry_dt", nullable = false)
    private Instant expiryDate;

    public void setConfirmedStatus() {
        setTokenStatus(TokenStatus.STATUS_CONFIRMED);
    }
}
