package com.grpc.demo.snakesladdergame;

import java.util.HashMap;
import java.util.Map;

public class Const {

    public static Map<Long, Long> MOVEMENT_MAP = Map.ofEntries(
            Map.entry(1L, 38L),
            Map.entry(4L, 14L),
            Map.entry(8L, 30L),
            Map.entry(21L, 42L),
            Map.entry(28L, 76L),
            Map.entry(32L, 10L),
            Map.entry(36L, 6L),
            Map.entry(50L, 67L),
            Map.entry(48L, 26L),
            Map.entry(62L, 18L),
            Map.entry(71L, 92L),
            Map.entry(80L, 99L),
            Map.entry(88L, 24L),
            Map.entry(95L, 56L),
            Map.entry(97L, 78L)
    );

    public static Map<Long, GameInstance> GAMES_MAP = new HashMap<>();


}
