package com.reactive.order.domain;

import com.reactive.order.dto.OrderStatus;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "purchase_order")
public class PurchaseOrder {

  @Id @GeneratedValue private Long id;
  private Long productId;
  private Long userId;
  private Long amount;

  @Enumerated(EnumType.STRING)
  private OrderStatus orderStatus;
}
