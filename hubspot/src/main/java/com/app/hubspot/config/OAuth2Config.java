package com.app.hubspot.config;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

/**
 * Configuration class for HubSpot OAuth2 authentication.
 */
@Configuration
public class OAuth2Config {

    /**
     * The actual values are in src/resources/application.properties.
     */

    @Value("${hubspot.client.id}")
    private String clientId;

    @Value("${hubspot.client.secret:}")
    private String clientSecret;

    @Value("${hubspot.redirect.uri}")
    private String redirectUri;

    @Value("${hubspot.scope}")
    private String scope;

    @Value("${hubspot.auth.url}")
    private String authUrl;
    
    @Value("${hubspot.token.url:https://api.hubapi.com/oauth/v1/token}")
    private String tokenUrl;

     /**
     * Generates the authorization URL for HubSpot OAuth2 flow exactly as per HubSpot documentation.
     *
     * @return The full authorization URL to redirect users to
     */
    public String getAuthorizationUrl() {
        // Generate a random state for CSRF protection
        String state = UUID.randomUUID().toString();

        System.out.println("PASSOU AQUI CARA");


        // Build the URL as per HubSpot documentation
        StringBuilder authUrlBuilder = new StringBuilder(authUrl);
        authUrlBuilder.append("?client_id=").append(encode(clientId));
        authUrlBuilder.append("&scope=").append(encode(scope));
        authUrlBuilder.append("&redirect_uri=").append(encode(redirectUri));
        
        // Add state parameter for security
        authUrlBuilder.append("&state=").append(encode(state));
        
        // Always include response_type=code as required by OAuth 2.0 standard
        authUrlBuilder.append("&response_type=code");

        System.out.println("PASSOU AQUI CARA");

        return authUrlBuilder.toString();
    }
    

    private String encode(String value) {
        return URLEncoder.encode(value, StandardCharsets.UTF_8);
    }
    
    // Getters para usar no serviço OAuth
    
    public String getClientId() {
        return clientId;
    }
    
    public String getClientSecret() {
        return clientSecret;
    }
    
    public String getRedirectUri() {
        return redirectUri;
    }
    
    public String getTokenUrl() {
        return tokenUrl;
    }

}
