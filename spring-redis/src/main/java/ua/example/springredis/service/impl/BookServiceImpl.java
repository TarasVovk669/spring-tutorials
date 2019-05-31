package ua.example.springredis.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisKeyValueTemplate;
import org.springframework.stereotype.Service;
import ua.example.springredis.model.Author;
import ua.example.springredis.model.Book;
import ua.example.springredis.repository.AuthorRepository;
import ua.example.springredis.repository.BookRepository;
import ua.example.springredis.service.AuthorService;
import ua.example.springredis.service.BookService;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

@Service
public class BookServiceImpl implements BookService {

    private final RedisKeyValueTemplate redisKVTemplate;

    private final BookRepository bookRepository;

    @Autowired
    public BookServiceImpl(RedisKeyValueTemplate redisKVTemplate, BookRepository bookRepository) {
        this.redisKVTemplate = redisKVTemplate;
        this.bookRepository = bookRepository;
    }

    @Override
    public Book getBook(String id) {
        return bookRepository.findById(id).get();
    }

    @Override
    public Set<Book> getBooks() {
        Set<Book> books = new HashSet<>();
        bookRepository.findAll().forEach(books::add);
        return books;
    }

    @Override
    public void delete(String id) {
        bookRepository.deleteById(id);
    }

    @Override
    public Book save(Book book) {
        return bookRepository.save(book);
    }

    @Override
    public Book update(Book book) {
        return redisKVTemplate.update(book);
    }

    @Override
    public Set<Book> saveList(Set<Book> books) {
        Set<Book> booksNew = new LinkedHashSet<>();
        books.forEach( book -> booksNew.add(bookRepository.save(book)));
        return booksNew;
    }
}
