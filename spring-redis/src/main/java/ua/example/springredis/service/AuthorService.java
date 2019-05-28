package ua.example.springredis.service;

import ua.example.springredis.model.Author;

import java.util.Set;

public interface AuthorService {

    Author getAuthor(Long id);

    Set<Author> getAll();

    void deleteAuthor(Long id);

}
