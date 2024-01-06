package org.example.service;

import com.google.protobuf.Timestamp;
import com.grpc.example.movie.MovieDto;
import com.grpc.example.movie.MovieSearchRequest;
import com.grpc.example.movie.MovieSearchResponse;
import com.grpc.example.movie.MovieServiceGrpc;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;
import org.example.repository.MovieRepository;

import java.time.Instant;
import java.time.ZoneId;
import java.util.Optional;
import java.util.stream.Collectors;

@GrpcService
@RequiredArgsConstructor
public class MovieService extends MovieServiceGrpc.MovieServiceImplBase {

    private final MovieRepository movieRepository;

    @Override
    public void getMovies(MovieSearchRequest request, StreamObserver<MovieSearchResponse> responseObserver) {
        Optional.of(movieRepository.getMoviesByGenre(request.getGenre()))
                .ifPresentOrElse(lists -> {
                            responseObserver.onNext(MovieSearchResponse.newBuilder()
                                    .addAllMovies(lists.stream()
                                            .map(e -> {
                                                Instant instant = e.getReleaseDate().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant();

                                                return MovieDto.newBuilder()
                                                        .setTitle(e.getTitle())
                                                        .setRating(e.getRating())
                                                        .setReleaseDate(Timestamp.newBuilder()
                                                                .setSeconds(instant.getEpochSecond())
                                                                .setNanos(instant.getNano())
                                                                .build())
                                                        .build();
                                            })
                                            .collect(Collectors.toList()))
                                    .build());
                            responseObserver.onCompleted();
                        },
                        () -> responseObserver.onError(Status.NOT_FOUND.withDescription("Movies Not Found").asRuntimeException()));

    }
}
