package com.reactive.user.userreactiveservice.service.impl;

import com.reactive.user.userreactiveservice.dto.TransactionRequestDto;
import com.reactive.user.userreactiveservice.dto.TransactionResponseDto;
import com.reactive.user.userreactiveservice.dto.TransactionStatus;
import com.reactive.user.userreactiveservice.repository.UserRepository;
import com.reactive.user.userreactiveservice.repository.UserTransactionRepository;
import com.reactive.user.userreactiveservice.service.UserTransactionService;
import com.reactive.user.userreactiveservice.util.DtoUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class UserTransactionServiceImpl implements UserTransactionService {

  private final UserRepository userRepository;
  private final UserTransactionRepository transactionRepository;

  @Override
  public Mono<TransactionResponseDto> create(TransactionRequestDto requestDto) {
    return this.userRepository
        .updateUserBalance(requestDto.getUserId(), requestDto.getAmount())
        .filter(Boolean::booleanValue)
        .map(b -> DtoUtil.toEntity(requestDto))
        .flatMap(this.transactionRepository::save)
        .map(ut -> DtoUtil.toDto(requestDto, TransactionStatus.APPROVED))
        .defaultIfEmpty(DtoUtil.toDto(requestDto, TransactionStatus.DECLINED));
  }

  @Override
  public Flux<TransactionResponseDto> getAllTrxByUserId(Long userId) {
    return this.transactionRepository
        .findAllByUserId(userId)
        .map(DtoUtil::toDto);
  }
}
