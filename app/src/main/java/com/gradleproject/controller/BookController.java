package com.gradleproject.controller;

import com.gradleproject.model.Book;
import com.gradleproject.service.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService){
        this.bookService = bookService;
    }

    // Retrieve a book by its id.
    @GetMapping("/{id}")
    public Book getBook(@PathVariable Long id) {
        return bookService.getBook(id);
    }

    // Update a book.
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Book updateBook(@PathVariable Long id, @RequestBody Book book) {
        if (!id.equals(book.getId())) {
            throw new IllegalArgumentException("ID in the path and request body must match");
        }
        return bookService.updateBook(book);
    }

    // Delete a book.
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
    }
}
