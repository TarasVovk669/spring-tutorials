package com.reactive.user.userreactiveservice.domain;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Builder
@Table(name = "t_transactions")
public class UserTransaction {

  @Id private Long id;
  private Long userId;
  private Long amount;
  private LocalDateTime date;
}
