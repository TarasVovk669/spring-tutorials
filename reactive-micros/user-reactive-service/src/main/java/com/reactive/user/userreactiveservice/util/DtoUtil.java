package com.reactive.user.userreactiveservice.util;

import com.reactive.user.userreactiveservice.domain.User;
import com.reactive.user.userreactiveservice.domain.UserTransaction;
import com.reactive.user.userreactiveservice.dto.TransactionRequestDto;
import com.reactive.user.userreactiveservice.dto.TransactionResponseDto;
import com.reactive.user.userreactiveservice.dto.TransactionStatus;
import com.reactive.user.userreactiveservice.dto.UserDto;
import java.time.LocalDateTime;

public class DtoUtil {

  public static UserDto toDto(User user) {
    return UserDto.builder()
        .id(user.getId())
        .name(user.getName())
        .balance(user.getBalance())
        .build();
  }

  public static User toEntity(UserDto dto) {
    return User.builder().id(dto.getId()).name(dto.getName()).balance(dto.getBalance()).build();
  }

  public static UserTransaction toEntity(TransactionRequestDto dto) {
    return UserTransaction.builder()
        .userId(dto.getUserId())
        .amount(dto.getAmount().longValue())
        .date(LocalDateTime.now())
        .build();
  }

  public static TransactionResponseDto toDto(
      TransactionRequestDto requestDto, TransactionStatus transactionStatus) {
    return TransactionResponseDto.builder()
        .userId(requestDto.getUserId())
        .amount(requestDto.getAmount())
        .status(transactionStatus)
        .build();
  }

  public static TransactionResponseDto toDto(UserTransaction userTransaction) {
    return TransactionResponseDto.builder()
        .id(userTransaction.getId())
        .userId(userTransaction.getUserId())
        .amount(userTransaction.getAmount().intValue())
        .status(TransactionStatus.APPROVED)
        .build();
  }
}
