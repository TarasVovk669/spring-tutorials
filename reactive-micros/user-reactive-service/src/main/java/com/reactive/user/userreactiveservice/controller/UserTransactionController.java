package com.reactive.user.userreactiveservice.controller;

import com.reactive.user.userreactiveservice.dto.TransactionRequestDto;
import com.reactive.user.userreactiveservice.dto.TransactionResponseDto;
import com.reactive.user.userreactiveservice.service.UserTransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/users/transactions")
@RequiredArgsConstructor
public class UserTransactionController {

  private final UserTransactionService userTransactionService;

  @PostMapping
  public Mono<TransactionResponseDto> create(@RequestBody Mono<TransactionRequestDto> dtoMono) {
    return dtoMono.flatMap(userTransactionService::create);
  }

  @GetMapping
  public Flux<TransactionResponseDto> getAllByUserId(@RequestParam("user_id") Long userId){
    return userTransactionService.getAllTrxByUserId(userId);
  }
}
