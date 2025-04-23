package com.app.hubspot.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ContactEvent {

    private Long eventId;

    private Long subscriptionId;

    private Long portalId;

    private Long appId;

    private Instant occurredAt;

    private String subscriptionType;

    private Integer attemptNumber;

    private Long objectId;

    private String changeFlag;

    private String changeSource;

    private String sourceId;

}