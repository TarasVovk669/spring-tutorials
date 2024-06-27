package com.reactive.webflux.demo.base.configuration;

import com.reactive.webflux.demo.base.dto.MathResponseDto;
import com.reactive.webflux.demo.base.dto.MultiplyRequestDto;
import com.reactive.webflux.demo.base.exception.InvalidInputNumberException;
import com.reactive.webflux.demo.base.service.ReactiveMathService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class RouteHandler {

  private final ReactiveMathService mathService;

  public Mono<ServerResponse> squareHandler(ServerRequest request) {
    var input = request.pathVariable("input");
    return ServerResponse.ok()
        .body(this.mathService.findSquare(Integer.valueOf(input)), MathResponseDto.class);
  }

  public Mono<ServerResponse> tableHandler(ServerRequest request) {
    var input = Integer.valueOf(request.pathVariable("input"));
    var response = this.mathService.listResult(input);

    return ServerResponse.ok().body(response, MathResponseDto.class);
  }

  public Mono<ServerResponse> tableStreamHandler(ServerRequest request) {
    var input = Integer.valueOf(request.pathVariable("input"));
    var response = this.mathService.listResult(input);

    return ServerResponse.ok()
        .contentType(MediaType.TEXT_EVENT_STREAM)
        .body(response, MathResponseDto.class);
  }

  public Mono<ServerResponse> multiplyHandler(ServerRequest request) {
    Mono<MultiplyRequestDto> multiplyRequestDtoMono = request.bodyToMono(MultiplyRequestDto.class);
    var response = this.mathService.multiply(multiplyRequestDtoMono);
    return ServerResponse.ok().body(response, MathResponseDto.class);
  }

  public Mono<ServerResponse> tableHandlerWithValidation(ServerRequest request) {
    var input = Integer.valueOf(request.pathVariable("input"));

    if (input < 10 || input > 20) {
      var exception =
          new InvalidInputNumberException("ER003", "Invalid Request", "input number is invalid");

      return Mono.error(exception);
    }
    var response = this.mathService.listResult(input);



    return ServerResponse.ok().body(response, MathResponseDto.class);
  }
}
