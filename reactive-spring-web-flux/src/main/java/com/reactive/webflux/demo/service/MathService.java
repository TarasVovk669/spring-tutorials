package com.reactive.webflux.demo.service;

import com.reactive.webflux.demo.dto.MathResponseDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.IntStream;

import static com.reactive.webflux.demo.util.SleepUtils.sleep;

@Service
public class MathService {

  public MathResponseDto findSquare(Integer number) {
    return new MathResponseDto(number * number);
  }

  public List<MathResponseDto> listResult(Integer number) {
    return IntStream.rangeClosed(1, 10)
        .peek(i -> sleep(1))
        .peek(i -> System.out.println("math-service processing: " + i))
        .mapToObj(i -> new MathResponseDto(i * number))
        .toList();
  }
}
