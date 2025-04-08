package com.gradleproject.service;

import com.gradleproject.model.Book;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
@Slf4j
public class BookService {

    // A simulated in-memory repository.
    private final Map<Long, Book> bookRepository = new ConcurrentHashMap<>();

    @PostConstruct
    public void init() {
        // Pre-populate the repository with some books.
        bookRepository.put(1L, new Book(1L, "1984", "George Orwell"));
        bookRepository.put(2L, new Book(2L, "Brave New World", "Aldous Huxley"));
    }

    // Retrieve a book with caching.
    @Cacheable(value = "books", key = "#id")
    public Book getBook(Long id) {
        log.info("Fetching book with id {} from repository...", id);
        // Simulate a delay in retrieving the book.
        try { Thread.sleep(1000); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
        return bookRepository.get(id);
    }

    // Update a book and refresh the cache.
    @CachePut(value = "books", key = "#book.id")
    public Book updateBook(Book book) {
        log.info("Updating book with id {} in repository...", book.getId());
        bookRepository.put(book.getId(), book);
        return book;
    }

    // Delete a book and evict it from the cache.
    @CacheEvict(value = "books", key = "#id")
    public void deleteBook(Long id) {
        log.info("Deleting book with id {} from repository...", id);
        bookRepository.remove(id);
    }

}