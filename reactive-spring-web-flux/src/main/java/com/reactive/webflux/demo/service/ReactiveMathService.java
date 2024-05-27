package com.reactive.webflux.demo.service;

import static com.reactive.webflux.demo.util.SleepUtils.sleep;

import com.reactive.webflux.demo.dto.MathResponseDto;

import java.time.Duration;
import java.util.List;
import java.util.stream.IntStream;

import com.reactive.webflux.demo.dto.MultiplyRequestDto;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ReactiveMathService {

  public Mono<MathResponseDto> findSquare(Integer number) {
    // return Mono.just(new MathResponseDto(number * number));
    return Mono.fromSupplier(() -> number * number).map(MathResponseDto::new);
  }

  public Flux<MathResponseDto> listResult(Integer number) {
    return Flux.range(1, 10)
        .delayElements(Duration.ofSeconds(1))
        .doOnNext(i -> System.out.println("math-service processing: " + i))
        .map(i -> new MathResponseDto(i * number));
  }

  public Mono<MathResponseDto> multiply(Mono<MultiplyRequestDto> multiplyRequestDto) {
    return multiplyRequestDto.map(dto -> dto.getOne() * dto.getTwo()).map(MathResponseDto::new);
  }
}
