package com.app.hubspot.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Map;

@Getter
@Setter
public class CreateContactResponseDTO {

    private Long id;

    private Map<String, Object> properties;

    private String createdAt;

    private String updatedAt;

    private Boolean archived;
}
