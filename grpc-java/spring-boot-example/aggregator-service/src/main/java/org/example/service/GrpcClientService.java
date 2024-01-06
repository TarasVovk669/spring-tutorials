package org.example.service;

import com.grpc.example.common.Genre;
import com.grpc.example.movie.MovieSearchRequest;
import com.grpc.example.movie.MovieServiceGrpc;
import com.grpc.example.user.UserGenreUpdateRequest;
import com.grpc.example.user.UserSearchRequest;
import com.grpc.example.user.UserServiceGrpc;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.example.dto.Movie;
import org.example.dto.RecommendationDto;
import org.example.dto.UserDto;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.stream.Collectors;

@Service
public class GrpcClientService {

    @GrpcClient("user-service")
    private UserServiceGrpc.UserServiceBlockingStub userStub;

    @GrpcClient("movie-service")
    private MovieServiceGrpc.MovieServiceBlockingStub movieStub;


    public RecommendationDto getRecommendations(Long userId) {
        var user = userStub.getUserGenre(UserSearchRequest.newBuilder()
                .setId(userId)
                .build());
        var movies = movieStub.getMovies(MovieSearchRequest.newBuilder()
                .setGenre(user.getGenre())
                .build());

        return RecommendationDto.builder()
                .user(UserDto.builder()
                        .id(user.getId())
                        .firstName(user.getFirstName())
                        .lastName(user.getLastName())
                        .birthdate(LocalDateTime.ofInstant(Instant.ofEpochSecond(user.getBirthDate().getSeconds(), user.getBirthDate().getNanos()), ZoneId.systemDefault()).toLocalDate())
                        .build())
                .movies(movies.getMoviesList().stream()
                        .map(e -> Movie.builder()
                                .title(e.getTitle())
                                .releaseDate(LocalDateTime.ofInstant(Instant.ofEpochSecond(e.getReleaseDate().getSeconds(), e.getReleaseDate().getNanos()), ZoneId.systemDefault()).toLocalDate())
                                .rating(e.getRating())
                                .build())
                        .collect(Collectors.toList()))
                .build();
    }

    public UserDto changeUserGenre(Long id, Genre genre) {
        var user = userStub.updateUserGenre(UserGenreUpdateRequest.newBuilder()
                .setId(id)
                .setGenre(genre)
                .build());
        return UserDto.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .birthdate(LocalDateTime.ofInstant(Instant.ofEpochSecond(user.getBirthDate().getSeconds(), user.getBirthDate().getNanos()), ZoneId.systemDefault()).toLocalDate())
                .build();
    }

}
