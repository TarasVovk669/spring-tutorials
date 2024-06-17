package com.reactive.order.domain;

import com.reactive.order.dto.OrderStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "purchase_order")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PurchaseOrder {

  @Id @GeneratedValue private Long id;
  private String productId;
  private Long userId;
  private Long amount;

  @Enumerated(EnumType.STRING)
  private OrderStatus orderStatus;
}
