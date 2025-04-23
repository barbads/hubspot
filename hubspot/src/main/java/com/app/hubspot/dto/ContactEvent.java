package com.app.hubspot.dto;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Entity
@Table(name = "contact_events")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ContactEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "event_id")
    private Long eventId;

    @Column(name = "subscription_id")
    private Long subscriptionId;

    @Column(name = "portal_id")
    private Long portalId;

    @Column(name = "app_id")
    private Long appId;

    @Column(name = "occurred_at")
    private Instant occurredAt;

    @Column(name = "subscription_type")
    private String subscriptionType;

    @Column(name = "attempt_number")
    private Integer attemptNumber;

    @Column(name = "object_id")
    private Long objectId;

    @Column(name = "change_flag")
    private String changeFlag;

    @Column(name = "change_source")
    private String changeSource;

    @Column(name = "source_id")
    private String sourceId;

}