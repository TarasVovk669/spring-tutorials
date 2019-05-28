package ua.example.springredis.service.impl;

import ua.example.springredis.model.Author;
import ua.example.springredis.repository.AuthorRepository;
import ua.example.springredis.service.AuthorService;

import java.util.Set;

public class AuthorServiceImpl implements AuthorService {


    private final AuthorRepository authorRepository;

    public AuthorServiceImpl(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    public Author getAuthor(Long id) {
        return authorRepository.findById(id).get();
    }

    @Override
    public Set<Author> getAll() {
        return null;//authorRepository.findAll().;
    }

    @Override
    public void deleteAuthor(Long id) {
        authorRepository.deleteById(id);
    }
}
