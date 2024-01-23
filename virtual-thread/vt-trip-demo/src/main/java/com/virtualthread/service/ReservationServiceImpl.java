package com.virtualthread.service;

import com.virtualthread.dto.Flight;
import com.virtualthread.dto.FlightReservationRequest;
import com.virtualthread.dto.FlightReservationResponse;
import com.virtualthread.external.api.ExternalApiSecond;
import java.util.Comparator;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReservationServiceImpl implements ReservationService {

  private static final Logger log = LoggerFactory.getLogger(ReservationServiceImpl.class);

  private final ExternalApiSecond client;

  @Override
  public FlightReservationResponse reserve(FlightReservationRequest request) {
    log.info("reserve with request: {}", request);
    var start = System.currentTimeMillis();

    var flights = client.search(request.departure(), request.arrival());
    var bestDeal = flights.stream().min(Comparator.comparingInt(Flight::price));
    var flight = bestDeal.orElseThrow(() -> new RuntimeException("Flight Not Found"));
    var reservationRequest =
        new FlightReservationRequest(
            request.departure(), request.arrival(), flight.flightNumber(), request.tripDate());
    var reserve = client.reserve(reservationRequest);

    log.info("Time : {} ms", System.currentTimeMillis() - start);
    return reserve;
  }
}
