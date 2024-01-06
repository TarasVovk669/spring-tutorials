package org.example.service;

import com.google.protobuf.Timestamp;
import com.grpc.example.user.UserGenreUpdateRequest;
import com.grpc.example.user.UserResponse;
import com.grpc.example.user.UserSearchRequest;
import com.grpc.example.user.UserServiceGrpc;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;
import org.example.repository.UserRepository;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.ZoneId;


@GrpcService
@RequiredArgsConstructor
public class UserService extends UserServiceGrpc.UserServiceImplBase {

    private final UserRepository userRepository;

    @Override
    public void getUserGenre(UserSearchRequest request, StreamObserver<UserResponse> responseObserver) {
        userRepository.findById(request.getId())
                .ifPresentOrElse(user -> {
                            Instant instant = user.getBirthdate().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant();

                            responseObserver.onNext(UserResponse.newBuilder()
                                    .setId(user.getId())
                                    .setFirstName(user.getFirstName())
                                    .setLastName(user.getLastName())
                                    .setGenre(user.getGenre())
                                    .setBirthDate(Timestamp.newBuilder()
                                            .setSeconds(instant.getEpochSecond())
                                            .setNanos(instant.getNano())
                                            .build())
                                    .build());
                            responseObserver.onCompleted();
                        },
                        () -> responseObserver.onError(Status.NOT_FOUND.withDescription("User Not Found").asRuntimeException()));

    }

    @Override
    @Transactional
    public void updateUserGenre(UserGenreUpdateRequest request, StreamObserver<UserResponse> responseObserver) {
        userRepository.findById(request.getId())
                .ifPresentOrElse(user -> {
                            user.setGenre(request.getGenre());

                            Instant instant = user.getBirthdate().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant();
                            responseObserver.onNext(UserResponse.newBuilder()
                                    .setId(user.getId())
                                    .setFirstName(user.getFirstName())
                                    .setLastName(user.getLastName())
                                    .setGenre(user.getGenre())
                                    .setBirthDate(Timestamp.newBuilder()
                                            .setSeconds(instant.getEpochSecond())
                                            .setNanos(instant.getNano())
                                            .build())
                                    .build());
                            responseObserver.onCompleted();
                        },
                        () -> responseObserver.onError(Status.NOT_FOUND.withDescription("User Not Found").asRuntimeException()));
    }


}
