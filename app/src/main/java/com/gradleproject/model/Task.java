package com.gradleproject.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "tasks", uniqueConstraints = @UniqueConstraint(columnNames = "name"))
@Data
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "GP_UNIQUE_ID")
    @SequenceGenerator(name = "GP_UNIQUE_ID", sequenceName = "GP_UNIQUE_ID", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String description;

    @Version
    private Long version;

}