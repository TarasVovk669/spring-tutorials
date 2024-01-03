package com.grpc.demo.utils;

import com.grpc.demo.gen.Balance;
import com.grpc.demo.gen.Currency;

import java.util.HashMap;
import java.util.Map;

public class Const {

    public static final Map<Long, Balance> BALANCE_MAP = new HashMap<>();

    static {
        BALANCE_MAP.put(5321L, Balance.newBuilder().setBalance(676786.89).setCurrency(Currency.USD).build());
        BALANCE_MAP.put(6547L, Balance.newBuilder().setBalance(1200.00).setCurrency(Currency.EUR).build());
        BALANCE_MAP.put(1234567L, Balance.newBuilder().setBalance(15600.00).setCurrency(Currency.JPY).build());
        BALANCE_MAP.put(9876543L, Balance.newBuilder().setBalance(789.15).setCurrency(Currency.JPY).build());
        BALANCE_MAP.put(123456789L, Balance.newBuilder().setBalance(678.90).setCurrency(Currency.EUR).build());

    }
}
