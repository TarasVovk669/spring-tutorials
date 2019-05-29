package ua.example.springredis.service;

import org.springframework.stereotype.Service;
import ua.example.springredis.model.Author;

import java.util.Set;

public interface AuthorService {

    Author getAuthor(String id);

    Set<Author> getAll();

    void deleteAuthor(String id);

    Author save(Author author);

}
