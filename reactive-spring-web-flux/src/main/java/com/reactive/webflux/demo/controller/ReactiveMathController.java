package com.reactive.webflux.demo.controller;

import com.reactive.webflux.demo.dto.MathResponseDto;
import com.reactive.webflux.demo.dto.MultiplyRequestDto;
import com.reactive.webflux.demo.exception.InvalidInputNumberException;
import com.reactive.webflux.demo.service.MathService;
import java.util.List;

import com.reactive.webflux.demo.service.ReactiveMathService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/reactive-math")
public class ReactiveMathController {

  private final ReactiveMathService mathService;

  @GetMapping("square/{input}")
  public Mono<MathResponseDto> findSquare(@PathVariable Integer input) {
    return mathService.findSquare(input);
  }

  @GetMapping("square/{input}/validated")
  public Mono<MathResponseDto> findSquareValidated(@PathVariable Integer input) {
    if (input < 10 || input > 20) {
      throw new InvalidInputNumberException(
          "ER001", "Invalid request", "number must be in range from 10 to 20");
    }
    return mathService.findSquare(input);
  }

  @GetMapping("square/{input}/reactive-validated")
  public Mono<MathResponseDto> findSquareReactiveValidated(@PathVariable Integer input) {
    return Mono.just(input)
        .handle(
            (number, sink) -> {
              if (number <= 20 && number >= 10) {
                sink.next(number);
              } else {
                sink.error(
                    new InvalidInputNumberException(
                        "ER002",
                        "Invalid request reactive",
                        "number must be in range from 10 to 20"));
              }
            })
        .cast(Integer.class)
        .flatMap(mathService::findSquare);
  }

  @GetMapping("square/{input}/reactive-validated-two")
  public Mono<ResponseEntity<MathResponseDto>> findSquareReactiveValidatedTw0(
      @PathVariable Integer input) {
    return Mono.just(input)
        .filter(integer -> integer >= 10 && integer <= 20)
        .flatMap(mathService::findSquare)
        .map(ResponseEntity::ok)
        .defaultIfEmpty(ResponseEntity.badRequest().build());
  }

  @GetMapping("table/{input}")
  public Flux<MathResponseDto> multiplyTable(@PathVariable Integer input) {
    return mathService.listResult(input);
  }

  @GetMapping(value = "table/{input}/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
  public Flux<MathResponseDto> multiplyTableStream(@PathVariable Integer input) {
    return mathService.listResult(input);
  }

  @PostMapping("multiply")
  public Mono<MathResponseDto> multiply(@RequestBody Mono<MultiplyRequestDto> multiplyRequestDto) {
    return mathService.multiply(multiplyRequestDto);
  }
}
