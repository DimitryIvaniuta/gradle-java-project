package com.gradleproject.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "events")
@Data
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "GP_UNIQUE_ID")
    @SequenceGenerator(name = "GP_UNIQUE_ID", sequenceName = "GP_UNIQUE_ID", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "event_timestamp", nullable = false)
    private LocalDateTime eventTimestamp;

    @Column(name = "event_type", nullable = false, length = 50)
    private String eventType;

    @Column(name = "payload")
    private String payload;

    // Constructors
    public Event() {
    }

    public Event(LocalDateTime eventTimestamp, String eventType, String payload) {
        this.eventTimestamp = eventTimestamp;
        this.eventType = eventType;
        this.payload = payload;
    }


}
