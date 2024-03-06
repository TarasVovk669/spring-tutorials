package com.micro.exchange.currencyexchangeservice.domain;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Data
@ToString
@Entity
@Builder
@Table(name = "exchange")
@NoArgsConstructor
@AllArgsConstructor
public class Exchange {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @Column(name = "from_curr")
    private String from;
    @Column(name = "to_curr")
    private String to;
    private BigDecimal conversionMultiple;
    private String env;
}
