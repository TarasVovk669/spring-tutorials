package ua.example.springredis.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.example.springredis.model.Author;
import ua.example.springredis.service.AuthorService;

import java.util.Set;

@RestController
public class AuthorController {

    private final AuthorService authorService;

    @Autowired
    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @GetMapping(value = "/authors/{id}")
    public ResponseEntity<Author> getAuthor(@PathVariable("id") Long id) {
        Author author = authorService.getAuthor(id);
        return new ResponseEntity<>(author, HttpStatus.FOUND);
    }

    @GetMapping(value = "/authors")
    public ResponseEntity<Set<Author>> getAuthor() {
        Set<Author> authors = authorService.getAll();
        return new ResponseEntity<>(authors, HttpStatus.FOUND);
    }


    @PostMapping(value = "/authors")
    public ResponseEntity<Author> getAuthor(@RequestBody Author author) {
        Author authorNew = authorService.save(author);
        return new ResponseEntity<>(authorNew, HttpStatus.CREATED);
    }

    @DeleteMapping(value = "/authors/{id}")
    public ResponseEntity<Long> deleteAuthor(@PathVariable("id") Long id) {
        authorService.deleteAuthor(id);
        return new ResponseEntity<>(id, HttpStatus.OK);
    }
}
