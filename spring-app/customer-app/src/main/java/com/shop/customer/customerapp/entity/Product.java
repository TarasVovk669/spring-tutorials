package com.shop.customer.customerapp.entity;

import java.math.BigDecimal;

public record Product(Long id, String name, String description, BigDecimal price) {}
