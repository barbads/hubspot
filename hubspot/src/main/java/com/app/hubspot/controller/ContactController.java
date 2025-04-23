package com.app.hubspot.controller;

import com.app.hubspot.dto.ContactEvent;
import com.app.hubspot.models.Contact;
import com.app.hubspot.models.TokenResponse;
import com.app.hubspot.repository.TokenResponseRepository;
import com.app.hubspot.service.ContactService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/auth")
public class ContactController {

    private static final Logger logger = LoggerFactory.getLogger(ContactController.class);

    private final TokenResponseRepository tokenResponseRepository;

    private final ContactService contactService;

    public ContactController(TokenResponseRepository tokenResponseRepository, ContactService contactService) {
        this.tokenResponseRepository = tokenResponseRepository;
        this.contactService = contactService;
    }

    /**
     * Endpoint to receive any JSON object
     *
     * @param contact The JSON request body
     * @return ResponseEntity with status and echo of received data
     */


    @PostMapping("/contact")
    public ResponseEntity createContact(@RequestBody Contact contact) throws Exception {
        // Log or process the received JSON data
        System.out.println("Received JSON data: " + contact.getClass());

        List<TokenResponse> tokens = tokenResponseRepository.findTokens();
        if (tokens.isEmpty()) {
            logger.error("No tokens found in the database.");
            return ResponseEntity.status(500).body("No tokens found in the database.");
        }

        List<TokenResponse> validTokens = tokens
                .stream().filter(token -> token.getExpiresAt().isAfter(LocalDateTime.now())).toList();

        if (validTokens.isEmpty()) {
            logger.error("No valid tokens found in the database.");
            return ResponseEntity.status(500).body("No valid tokens found in the database. Create a new URL to generate a new valid token.");
        }

        contactService.createContact(contact, validTokens.get(0).getAccessToken());

        // Return the received data as confirmation
        return ResponseEntity.ok().build();
    }

//    @PostMapping("/contact/webhook")
//    public ResponseEntity webhookContact(@RequestBody ContactEvent contactEvent) throws Exception {
//        // Log or process the received JSON data
//        System.out.println("Received JSON data: " + contactEvent.getClass());
//
//        List<TokenResponse> tokens = tokenResponseRepository.findTokens();
//
//        if (tokens.isEmpty()) {
//            logger.error("No tokens found in the database.");
//            return ResponseEntity.status(500).body("No tokens found in the database.");
//        }
//
//        List<TokenResponse> validTokens = tokens
//                .stream().filter(token -> token.getExpiresAt().isAfter(LocalDateTime.now())).toList();
//
//        if (validTokens.isEmpty()) {
//            logger.error("No valid tokens found in the database.");
//            return ResponseEntity.status(500).body("No valid tokens found in the database. Create a new URL to generate a new valid token.");
//        }
//
//        // Return the received data as confirmation
//        return ResponseEntity.ok().build();
//    }
}
