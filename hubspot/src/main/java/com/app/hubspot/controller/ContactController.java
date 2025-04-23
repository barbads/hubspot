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
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class ContactController {

    private static final Logger logger = LoggerFactory.getLogger(ContactController.class);

    private final TokenResponseRepository tokenResponseRepository;

    private final ContactService contactService;

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

        List<TokenResponse> tokens = tokenResponseRepository.findTokens(LocalDateTime.now());

        contactService.createContact(contact, tokens.getFirst().getAccessToken());

        // Return the received data as confirmation
        return ResponseEntity.ok().build();
    }

    @PostMapping("/contact/webhook")
    public ResponseEntity webhookContact(@RequestBody ContactEvent contactEvent) throws Exception {
        // Log or process the received JSON data
        System.out.println("Received JSON data: " + contactEvent.getClass());



        List<TokenResponse> tokens = tokenResponseRepository.findTokens(LocalDateTime.now());

        //contactService.createContact(contact, tokens.getFirst().getAccessToken());

        // Return the received data as confirmation
        return ResponseEntity.ok().build();
    }
}
