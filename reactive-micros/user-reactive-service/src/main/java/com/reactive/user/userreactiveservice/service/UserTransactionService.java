package com.reactive.user.userreactiveservice.service;

import com.reactive.user.userreactiveservice.dto.TransactionRequestDto;
import com.reactive.user.userreactiveservice.dto.TransactionResponseDto;
import com.reactive.user.userreactiveservice.dto.UserDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserTransactionService {

    Mono<TransactionResponseDto> create(TransactionRequestDto dto);

    Flux<TransactionResponseDto> getAllTrxByUserId(Long userId);
}
