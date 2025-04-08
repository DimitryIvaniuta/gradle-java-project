package com.gradleproject.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
@ToString
public class Book implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private Long id;
    private String title;
    private String author;

    // No-arg constructor is necessary for serialization/deserialization.
    public Book() {
        //
    }

    public Book(Long id, String title, String author) {
        this.id = id;
        this.title = title;
        this.author = author;
    }

}