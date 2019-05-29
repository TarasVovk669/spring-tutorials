package ua.example.springredis.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.example.springredis.error.EntityNotFoundException;
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
    public ResponseEntity<Author> getAuthor(@PathVariable("id") String id) {
        Author author = authorService.getAuthor(id);
        return new ResponseEntity<>(author, HttpStatus.FOUND);
    }

    @GetMapping(value = "/authors")
    public ResponseEntity<Set<Author>> getAuthors() {
        Set<Author> authors = authorService.getAuthors();
        return new ResponseEntity<>(authors, HttpStatus.FOUND);
    }


    @PostMapping(value = "/authors")
    public ResponseEntity<Author> createAuthor(@RequestBody Author author) {
        Author authorNew = authorService.save(author);
        return new ResponseEntity<>(authorNew, HttpStatus.CREATED);
    }

    @PutMapping(value = "/authors")
    public ResponseEntity<Author> updateAuthor(@RequestBody Author author) throws EntityNotFoundException {

        if (author.getId() == null) {
            throw new EntityNotFoundException();
        }

        Author authorNew = authorService.update(author);
        return new ResponseEntity<>(authorNew, HttpStatus.OK);
    }

    @DeleteMapping(value = "/authors/{id}")
    public ResponseEntity<String> deleteAuthor(@PathVariable("id") String id) {
        authorService.delete(id);
        return new ResponseEntity<>(id, HttpStatus.OK);
    }
}
