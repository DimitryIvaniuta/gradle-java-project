package com.gradleproject.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;

@Entity
@Table(name = "users")

@Data
@Slf4j
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "GP_UNIQUE_ID")
    @SequenceGenerator(name = "GP_UNIQUE_ID", sequenceName = "GP_UNIQUE_ID", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "login", nullable = false)
    private String login;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    public User() {
        this.createdAt = LocalDateTime.now();
    }

    public User(String login, String name, String email, String password) {
        this.login = login;
        this.name = name;
        this.email = email;
        this.password = password;
        this.createdAt = LocalDateTime.now();
    }

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

}
