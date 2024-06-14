package com.reactive.user.userreactiveservice.controller;

import com.reactive.user.userreactiveservice.dto.UserDto;
import com.reactive.user.userreactiveservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

  private final UserService userService;

  @GetMapping
  public Flux<UserDto> getAll() {
    return userService.all();
  }

  @GetMapping("/{id}")
  public Mono<ResponseEntity<UserDto>> getOne(@PathVariable Long id) {
    return userService
        .getOne(id)
        .map(ResponseEntity::ok)
        .defaultIfEmpty(ResponseEntity.notFound().build());
  }

  @PostMapping
  public Mono<UserDto> create(@RequestBody Mono<UserDto> dtoMono) {
    return userService.create(dtoMono);
  }

  @PutMapping("/{id}")
  public Mono<ResponseEntity<UserDto>> update(
      @PathVariable Long id, @RequestBody Mono<UserDto> dtoMono) {
    return userService
        .update(id, dtoMono)
        .map(ResponseEntity::ok)
        .defaultIfEmpty(ResponseEntity.notFound().build());
  }

  @DeleteMapping("/{id}")
  public Mono<Void> delete(@PathVariable Long id) {
    return userService.delete(id);
  }
}
