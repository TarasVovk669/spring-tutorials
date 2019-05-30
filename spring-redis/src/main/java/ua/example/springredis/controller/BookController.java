package ua.example.springredis.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.example.springredis.error.EntityNotFoundException;
import ua.example.springredis.model.Author;
import ua.example.springredis.model.Book;
import ua.example.springredis.service.AuthorService;
import ua.example.springredis.service.BookService;

import java.util.Set;

@RestController
public class BookController {

    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping(value = "/books/{id}")
    public ResponseEntity<Book> getBook(@PathVariable("id") String id) {
        Book book = bookService.getBook(id);
        return new ResponseEntity<>(book, HttpStatus.FOUND);
    }

    @GetMapping(value = "/books")
    public ResponseEntity<Set<Book>> getBooks() {
        Set<Book> books = bookService.getBooks();
        return new ResponseEntity<>(books, HttpStatus.FOUND);
    }


    @PostMapping(value = "/books")
    public ResponseEntity<Book> createBook(@RequestBody Book book) {
        Book bookNew = bookService.save(book);
        return new ResponseEntity<>(bookNew, HttpStatus.CREATED);
    }

    @PutMapping(value = "/books")
    public ResponseEntity<Book> updateBook(@RequestBody Book book) throws EntityNotFoundException {

        if (book.getId() == null) {
            throw new EntityNotFoundException();
        }

        Book bookNew = bookService.update(book);
        return new ResponseEntity<>(bookNew, HttpStatus.OK);
    }

    @DeleteMapping(value = "/books/{id}")
    public ResponseEntity<String> deleteBook(@PathVariable("id") String id) {
        bookService.delete(id);
        return new ResponseEntity<>(id, HttpStatus.OK);
    }
}
