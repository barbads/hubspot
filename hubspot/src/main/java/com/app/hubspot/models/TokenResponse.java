package com.app.hubspot.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "tokens")
@Getter
@Setter
public class TokenResponse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "access_token")
    private String accessToken;

    @Column(name = "refresh_token")
    private String refreshToken;

    @Column(name = "token_type")
    private String tokenType;

    @Column(name = "expires_in")
    private Long expiresIn;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "expires_at")
    private LocalDateTime expiresAt;


    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();

        if (this.expiresIn != null && this.expiresIn > 0) {
            this.expiresAt = this.createdAt.plusSeconds(this.expiresIn);
        } else {
            this.expiresAt = this.createdAt.plusHours(1);
            // Se expiresIn for nulo, vamos definir um valor padrão
            if (this.expiresIn == null) {
                this.expiresIn = 3600L; // 1 hora em segundos
            }
        }

        // Logging para debug
        System.out.println("TokenResponse.prePersist: createdAt=" + this.createdAt +
                ", expiresIn=" + this.expiresIn +
                ", expiresAt=" + this.expiresAt);
    }
}