package com.app.hubspot.models;

import com.fasterxml.jackson.annotation.JsonProperty;
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

    @JsonProperty("access_token")
    @Column(name = "access_token")
    private String accessToken;

    @JsonProperty("refresh_token")
    @Column(name = "refresh_token")
    private String refreshToken;

    @JsonProperty("token_type")
    @Column(name = "token_type")
    private String tokenType;

    @JsonProperty("expires_in")
    @Transient
    private Long expiresIn;

    @JsonProperty("created_at")
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