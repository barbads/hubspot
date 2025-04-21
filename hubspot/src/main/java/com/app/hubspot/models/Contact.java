package com.app.hubspot.models;

import org.springframework.boot.autoconfigure.domain.EntityScan;

import java.io.Serializable;

@EntityScan
public class Contact implements Serializable {

    private static final long serialVersionUID = 1L;

    private String login;

    public String getLogin() {
        return login;
    }

}
