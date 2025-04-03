package com.gradleproject.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

import java.time.LocalDateTime;

/**
 * AuditEntry represents an audit record for tracking significant events in the system.
 * This entity is mapped to the "audit_entry" table in the database.
 *
 * <p>Key fields include:
 * <ul>
 *   <li>{@code id} - The primary key, automatically generated.</li>
 *   <li>{@code eventDate} - The timestamp when the audit event occurred.</li>
 *   <li>{@code eventType} - A short description or code identifying the type of event (e.g., "CREATE", "UPDATE", "DELETE").</li>
 *   <li>{@code message} - Detailed information about the event.</li>
 *   <li>{@code userId} - The identifier of the user who triggered the event (optional).</li>
 * </ul>
 *
 * <p>This entity uses JPA annotations to define its mapping, and Hibernate manages its lifecycle.
 */
@Entity
@Table(name = "audit_entry")
public class AuditEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "GP_UNIQUE_ID")
    @SequenceGenerator(name = "GP_UNIQUE_ID", sequenceName = "GP_UNIQUE_ID", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "event_date", nullable = false)
    private LocalDateTime eventDate;

    @Column(name = "event_type", nullable = false, length = 100)
    private String eventType;

    @Column(name = "message", columnDefinition = "TEXT")
    private String message;

    @Column(name = "user_id")
    private Long userId;

    /**
     * Default constructor required by JPA.
     */
    public AuditEntry() {
    }

    /**
     * Constructs an AuditEntry with the specified details.
     *
     * @param eventDate the timestamp when the event occurred.
     * @param eventType a short code or description of the event.
     * @param message   detailed information about the event.
     * @param userId    the identifier of the user who triggered the event.
     */
    public AuditEntry(LocalDateTime eventDate, String eventType, String message, Long userId) {
        this.eventDate = eventDate;
        this.eventType = eventType;
        this.message = message;
        this.userId = userId;
    }

    // Getters and setters

    public Long getId() {
        return id;
    }

    public LocalDateTime getEventDate() {
        return eventDate;
    }

    public void setEventDate(LocalDateTime eventDate) {
        this.eventDate = eventDate;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "AuditEntry{" +
                "id=" + id +
                ", eventDate=" + eventDate +
                ", eventType='" + eventType + '\'' +
                ", message='" + message + '\'' +
                ", userId=" + userId +
                '}';
    }
}
