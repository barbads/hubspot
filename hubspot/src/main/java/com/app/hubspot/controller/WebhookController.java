package com.app.hubspot.controller;

import com.app.hubspot.models.Contact;
import com.app.hubspot.models.TokenResponse;
import com.app.hubspot.service.WebhookService;
import org.springframework. http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/api/webhook")
public class WebhookController {

    private static final Logger logger = LoggerFactory.getLogger(WebhookController.class);

    private final WebhookService webhookService;

    public WebhookController(WebhookService webhookService) {
        this.webhookService = webhookService;
    }




    /**
     * Endpoint to receive any JSON object
     *
     * @param requestBody The JSON request body
     * @return ResponseEntity with status and echo of received data
     */
    @PostMapping("/receive")
    public ResponseEntity<Contact> receiveJson(@RequestBody TokenResponse requestBody) throws Exception {
        // Log or process the received JSON data
        System.out.println("Received JSON data: " + requestBody.getClass());

        // You can perform any operations with the received data here

        // Return the received data as confirmation
        return new ResponseEntity<Contact>(HttpStatus.OK);
    }
}