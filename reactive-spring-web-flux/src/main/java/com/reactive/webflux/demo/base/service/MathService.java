package com.reactive.webflux.demo.base.service;

import static com.reactive.webflux.demo.base.util.SleepUtils.sleep;

import com.reactive.webflux.demo.base.dto.MathResponseDto;
import java.util.List;
import java.util.stream.IntStream;
import org.springframework.stereotype.Service;

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
