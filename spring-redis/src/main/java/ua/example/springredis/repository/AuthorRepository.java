package ua.example.springredis.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ua.example.springredis.model.Author;

@Repository
public interface AuthorRepository extends CrudRepository<Author, String> {

}
