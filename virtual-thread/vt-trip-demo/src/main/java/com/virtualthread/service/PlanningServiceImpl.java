package com.virtualthread.service;

import com.virtualthread.dto.TripPlan;
import com.virtualthread.external.api.ExternalApiOne;
import java.util.Collections;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PlanningServiceImpl implements PlanningService {

  private static final Logger log = LoggerFactory.getLogger(PlanningServiceImpl.class);

  private final ExternalApiOne client;
  private final ExecutorService executor;

  @Override
  public TripPlan getTripPlan(String code) {
    log.info("Get trip_plan with code: {}", code);
    var start = System.currentTimeMillis();

    var accommodations =
        CompletableFuture.supplyAsync(() -> client.getAccommodations(code), executor)
            .exceptionally(e -> Collections.emptyList());
    var events =
        CompletableFuture.supplyAsync(() -> client.getEvents(code))
            .exceptionally(e -> Collections.emptyList());
    var transportation =
        CompletableFuture.supplyAsync(() -> client.getTransportation(code))
            .exceptionally(e -> null);
    var weather =
        CompletableFuture.supplyAsync(() -> client.getWeather(code)).exceptionally(e -> null);
    var recommendation =
        CompletableFuture.supplyAsync(() -> client.getRecommendation(code))
            .exceptionally(e -> null);

    var tripPlan =
        new TripPlan(
            code,
            accommodations.join(),
            weather.join(),
            events.join(),
            recommendation.join(),
            transportation.join());

    var end = System.currentTimeMillis();
    log.info("Time : {} ms", end - start);
    return tripPlan;
  }
}
