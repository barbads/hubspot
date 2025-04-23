package com.app.hubspot.repository;

import com.app.hubspot.models.TokenResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.time.LocalDateTime;

@Repository
public interface TokenResponseRepository extends JpaRepository<TokenResponse, Long> {

    @Query("SELECT t FROM TokenResponse t ORDER BY t.createdAt DESC")
    Optional<TokenResponse> findTokens();

}

