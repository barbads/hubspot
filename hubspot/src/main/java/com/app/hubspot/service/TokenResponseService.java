package com.app.hubspot.service;

import com.app.hubspot.models.TokenResponse;
import com.app.hubspot.repository.TokenResponseRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class TokenResponseService {

    private static final Logger logger = LoggerFactory.getLogger(TokenResponseService.class);

    @Autowired
    private TokenResponseRepository tokenRepository;

    /**
     * Salva um novo token recebido do HubSpot.
     *
     * @param tokenResponse O token recebido do HubSpot
     * @return O token salvo
     */
    @Transactional
    public TokenResponse saveToken(TokenResponse tokenResponse) {
        logger.info("Salvando novo token de acesso");

        // Os campos createdAt, expiresAt e isActive são definidos pelo método prePersist
        TokenResponse savedToken = tokenRepository.save(tokenResponse);
        logger.info("Token salvo com sucesso. ID: {}", savedToken.getId());

        return savedToken;
    }

    /**
     * Busca o token ativo mais recente.
     *
     * @return Optional contendo o token ativo mais recente, se existir
     */
    public Optional<TokenResponse> getLatestActiveToken() {
        return tokenRepository.findTokens()
                .filter(token -> LocalDateTime.now().isBefore(token.getExpiresAt()));
    }

    /**
     * Verifica se há um token válido disponível.
     *
     * @return true se houver um token válido e ativo
     */
    public boolean hasValidToken() {
        Optional<TokenResponse> tokenOpt = getLatestActiveToken();

        if (tokenOpt.isEmpty()) {
            return false;
        }

        TokenResponse token = tokenOpt.get();
        LocalDateTime now = LocalDateTime.now();

        // Verificar se o token está expirado
        boolean isValid = now.isBefore(token.getExpiresAt());

        if (!isValid) {
            logger.info("Token encontrado, mas está expirado ou inativo");
        }

        return isValid;
    }

    /**
     * Obtém o token de acesso atual para uso nas chamadas de API.
     *
     * @return O token de acesso, ou null se não houver token válido
     */
    public String getCurrentAccessToken() {
        Optional<TokenResponse> tokenOpt = getLatestActiveToken();

        if (tokenOpt.isPresent() &&
                LocalDateTime.now().isBefore(tokenOpt.get().getExpiresAt())) {
            return tokenOpt.get().getAccessToken();
        }

        logger.warn("Não há token de acesso válido disponível");
        return null;
    }
}