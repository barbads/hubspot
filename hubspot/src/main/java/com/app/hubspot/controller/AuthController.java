package com.app.hubspot.controller;

import com.app.hubspot.config.OAuth2Config;
import com.app.hubspot.dto.TokenResponseDTO;
import com.app.hubspot.service.OAuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*") // Para permitir chamadas de diferentes origens em ambiente de desenvolvimento
public class AuthController {

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);
    
    @Autowired
    private OAuth2Config oAuth2Config;
    
    @Autowired
    private OAuthService oAuthService;

    /**
     * Endpoint para gerar a URL de autorização conforme documentação do HubSpot
     * 
     * @return A URL de autorização completa
     */
    @GetMapping("/authorize")
    public ResponseEntity<String> generateAuthorizationUrl() {
        try {
            String authUrl = oAuth2Config.getAuthorizationUrl();
            System.out.println("URL de autorização gerada: " + authUrl);
            return ResponseEntity.ok(authUrl);
        } catch (Exception e) {
            System.err.println("Erro ao gerar URL de autorização: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }
    
    /**
     * Endpoint para processar o callback OAuth, recebendo o código de autorização
     * e trocando-o por um token de acesso.
     *
     * @param code O código de autorização fornecido pelo HubSpot
     * @return ResponseEntity contendo o token de acesso
     */
    @GetMapping("/callback")
    public ResponseEntity<TokenResponseDTO> processOAuthCallback(@RequestParam("code") String code) {
        logger.info("Recebido código de autorização: {}", code);
        
        try {
            TokenResponseDTO tokenResponseDto = oAuthService.exchangeCodeForToken(code);
            return ResponseEntity.ok(tokenResponseDto);
        } catch (Exception e) {
            logger.error("Erro ao processar callback OAuth: {}", e.getMessage(), e);
            throw e;
        }
    }
}