package com.app.hubspot.service;

import com.app.hubspot.dto.CreateContactResponseDTO;
import com.app.hubspot.models.Contact;
import com.app.hubspot.repository.ContactRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class ContactService {

    private static final String HUBSPOT_CONTACTS_URL = "https://api.hubapi.com/crm/v3/objects/contacts";

    private final RestTemplate restTemplate;
    private final ContactRepository contactRepository;

    public void createContact(Contact contact, String accessToken) {
        // Montar o mapa de propriedades
        Map<String, Object> properties = new HashMap<>();
        properties.put("email", contact.getEmail());
        properties.put("firstname", contact.getFirstname());
        properties.put("lastname", contact.getLastname());

        if (contact.getPhone() != null) properties.put("phone", contact.getPhone());
        if (contact.getCompany() != null) properties.put("company", contact.getCompany());
        if (contact.getWebsite() != null) properties.put("website", contact.getWebsite());
        if (contact.getLifecyclestage() != null) properties.put("lifecyclestage", contact.getLifecyclestage());

        // Estrutura do corpo da requisição conforme a API do HubSpot
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("properties", properties);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(accessToken);

        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(requestBody, headers);

        ResponseEntity<CreateContactResponseDTO> response = restTemplate.exchange(
                HUBSPOT_CONTACTS_URL,
                HttpMethod.POST,
                requestEntity,
                CreateContactResponseDTO.class
        );

        if (response.getStatusCode().is2xxSuccessful()) {
            contact.setObjectId(response.getBody().getId());
            System.out.println("Contato criado com sucesso: " + response.getBody());
            saveContact(contact);
            CreateContactResponseDTO createContactResponseDto = response.getBody();
            update(createContactResponseDto);
        } else {
            System.err.println("Erro ao criar contato: " + response.getStatusCode() + " - " + response.getBody());
        }


    }

    public void update(CreateContactResponseDTO createContactResponseDTO) {
        Contact contact = contactRepository.contatoPorObjectId(createContactResponseDTO.getId());
        contact.setObjectId(createContactResponseDTO.getId());
        contact.setSubscriptionStatus(Contact.SubscriptionStatus.CREATED);
        contactRepository.save(contact);
    }

    private void saveContact(Contact contact) {
        contact.setSubscriptionStatus(Contact.SubscriptionStatus.SENT);
        contactRepository.save(contact);
    }
}

