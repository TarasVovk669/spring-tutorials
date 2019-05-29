package ua.example.springredis.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisKeyValueTemplate;
import org.springframework.stereotype.Service;
import ua.example.springredis.model.Author;
import ua.example.springredis.repository.AuthorRepository;
import ua.example.springredis.service.AuthorService;

import java.util.HashSet;
import java.util.Set;

@Service
public class AuthorServiceImpl implements AuthorService {

    private final RedisKeyValueTemplate redisKVTemplate;

    private final AuthorRepository authorRepository;

    @Autowired
    public AuthorServiceImpl(RedisKeyValueTemplate redisKVTemplate, AuthorRepository authorRepository) {
        this.redisKVTemplate = redisKVTemplate;
        this.authorRepository = authorRepository;
    }

    @Override
    public Author getAuthor(String id) {
        return authorRepository.findById(id).get();
    }

    @Override
    public Set<Author> getAuthors() {
        Set<Author> authors = new HashSet<>();
        authorRepository.findAll().forEach(authors::add);
        return authors;
    }

    @Override
    public void delete(String id) {
        authorRepository.deleteById(id);
    }

    @Override
    public Author save(Author author) {
        return authorRepository.save(author);
    }

    @Override
    public Author update(Author author) {
        return redisKVTemplate.update(author);
    }
}
