package com.reactive.webflux.demo.base.controller;

import com.reactive.webflux.demo.base.dto.MathResponseDto;
import com.reactive.webflux.demo.base.service.MathService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/math")
public class MathController {

  private final MathService mathService;

  @GetMapping("square/{input}")
  public MathResponseDto findSquare(@PathVariable Integer input) {
    return mathService.findSquare(input);
  }

  @GetMapping("table/{input}")
  public List<MathResponseDto> multiplyTable(@PathVariable Integer input) {
    return mathService.listResult(input);
  }
}
