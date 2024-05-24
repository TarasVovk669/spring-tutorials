package com.shop.customer.customerapp.entity;

import java.util.UUID;

public record ReviewProduct(UUID id, Long prodId, Integer rating, String value) {
}
