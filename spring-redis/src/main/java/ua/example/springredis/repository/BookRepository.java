package ua.example.springredis.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ua.example.springredis.model.Author;
import ua.example.springredis.model.Book;

@Repository
public interface BookRepository extends CrudRepository<Book, String> {

}
