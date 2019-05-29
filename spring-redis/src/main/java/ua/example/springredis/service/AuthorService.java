package ua.example.springredis.service;

import ua.example.springredis.model.Author;

import java.util.Set;

public interface AuthorService {

    Author getAuthor(String id);

    Set<Author> getAuthors();

    void delete(String id);

    Author save(Author author);

    Author update(Author author);


}
