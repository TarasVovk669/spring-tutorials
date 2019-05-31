package ua.example.springredis.service;

import ua.example.springredis.model.Book;

import java.util.Set;

public interface BookService {

    Book getBook(String id);

    Set<Book> getBooks();

    void delete(String id);

    Book save(Book book);

    Book update(Book book);

    Set<Book> saveList(Set<Book> books);
}
