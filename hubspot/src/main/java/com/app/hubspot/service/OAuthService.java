package com.app.hubspot.service;

import com.app.hubspot.config.OAuth2Config;
import com.app.hubspot.models.TokenResponse;
import org.antlr.v4.runtime.Token;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
public class OAuthService {

    private static final Logger logger = LoggerFactory.getLogger(OAuthService.class);

    @Autowired
    private OAuth2Config oAuth2Config;

    @Autowired
    private RestTemplate restTemplate;

    /**
     * Troca o código de autorização por um token de acesso.
     *
     * @param code O código de autorização recebido do HubSpot
     * @return O objeto TokenResponse com os tokens
     */
    public TokenResponse exchangeCodeForToken(String code) {
        logger.info("Trocando código por token de acesso...");
        logger.info("Código recebido: {}", code);

        // Configuração do cabeçalho e corpo da requisição
        // O HubSpot espera que o corpo da requisição esteja no formato application/x-www-form-urlencoded
        // conforme a documentação da API.

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> formParams = new LinkedMultiValueMap<>();
        formParams.add("grant_type", "authorization_code");
        formParams.add("client_id", oAuth2Config.getClientId());
        formParams.add("client_secret", oAuth2Config.getClientSecret());
        formParams.add("redirect_uri", oAuth2Config.getRedirectUri());
        formParams.add("code", code);

        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(formParams, headers);

        ResponseEntity<TokenResponse> responseEntity = restTemplate.exchange(
                oAuth2Config.getTokenUrl(),
                HttpMethod.POST,
                requestEntity,
                TokenResponse.class);

        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            logger.info("Código trocado com sucesso por token de acesso");
            return responseEntity.getBody();
        } else {
            logger.error("Erro ao trocar código por token: {}", responseEntity.getStatusCode());
            throw new RuntimeException("Falha ao obter token de acesso: " + responseEntity.getStatusCode());
        }
    }
}