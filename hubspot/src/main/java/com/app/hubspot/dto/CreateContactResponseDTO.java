package com.app.hubspot.dto;

import com.app.hubspot.models.Contact;
import com.app.hubspot.repository.ContactRepository;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;

@AllArgsConstructor
@Getter
@Setter
public class CreateContactResponseDTO {

    private final ContactRepository contactRepository;

    private Long id;

    private Map<String, Object> properties;

    private String createdAt;

    private String updatedAt;

    private Boolean archived;


}
