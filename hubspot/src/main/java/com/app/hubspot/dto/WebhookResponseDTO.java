package com.app.hubspot.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class WebhookResponseDTO {

    private String email;


    private String firstname;


    private String lastname;


    private String phone;


    private String company;


    private String website;


    private String lifecyclestage;

}