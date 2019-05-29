package ua.example.springredis.service.impl;

import org.springframework.stereotype.Service;
import ua.example.springredis.model.Author;
import ua.example.springredis.repository.AuthorRepository;
import ua.example.springredis.service.AuthorService;

import java.util.HashSet;
import java.util.Set;

@Service
public class AuthorServiceImpl implements AuthorService {


    private final AuthorRepository authorRepository;

    public AuthorServiceImpl(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    public Author getAuthor(String id) {
        return authorRepository.findById(id).get();
    }

    @Override
    public Set<Author> getAll() {
        Set<Author> authors = new HashSet<>();
        authorRepository.findAll().forEach(authors::add);
        return authors;
    }

    @Override
    public void deleteAuthor(String id) {
        authorRepository.deleteById(id);
    }

    @Override
    public Author save(Author author) {
        return authorRepository.save(author);
    }
}
