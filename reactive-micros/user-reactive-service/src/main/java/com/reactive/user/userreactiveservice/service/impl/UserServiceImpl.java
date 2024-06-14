package com.reactive.user.userreactiveservice.service.impl;

import com.reactive.user.userreactiveservice.dto.UserDto;
import com.reactive.user.userreactiveservice.repository.UserRepository;
import com.reactive.user.userreactiveservice.service.UserService;
import com.reactive.user.userreactiveservice.util.DtoUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;

  @Override
  public Flux<UserDto> all() {
    return userRepository.findAll().map(DtoUtil::toDto);
  }

  @Override
  public Mono<UserDto> getOne(Long id) {
    return userRepository.findById(id).map(DtoUtil::toDto);
  }

  @Override
  public Mono<UserDto> create(Mono<UserDto> userDtoMono) {
    return userDtoMono.map(DtoUtil::toEntity).flatMap(userRepository::save).map(DtoUtil::toDto);
  }

  @Override
  public Mono<UserDto> update(Long id, Mono<UserDto> userDtoMono) {
    return userRepository
        .findById(id)
        .flatMap(user -> userDtoMono.map(DtoUtil::toEntity).doOnNext(e -> e.setId(id)))
        .flatMap(userRepository::save)
        .map(DtoUtil::toDto);
  }

  @Override
  public Mono<Void> delete(Long id) {
    return userRepository.deleteById(id);
  }
}
