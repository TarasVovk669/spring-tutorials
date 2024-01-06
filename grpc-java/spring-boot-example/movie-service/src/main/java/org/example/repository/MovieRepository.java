package org.example.repository;

import com.grpc.example.common.Genre;
import org.example.domain.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MovieRepository extends JpaRepository<Movie,Long> {

    List<Movie> getMoviesByGenre(Genre genre);
}
