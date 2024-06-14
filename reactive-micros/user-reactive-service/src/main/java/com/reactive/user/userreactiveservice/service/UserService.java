package com.reactive.user.userreactiveservice.service;

import com.reactive.user.userreactiveservice.dto.UserDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserService {
    Flux<UserDto> all();

    Mono<UserDto> getOne(Long id);

    Mono<UserDto> create(Mono<UserDto> userDtoMono);

    Mono<UserDto> update(Long id, Mono<UserDto> userDtoMono);

    Mono<Void> delete(Long id);
}
