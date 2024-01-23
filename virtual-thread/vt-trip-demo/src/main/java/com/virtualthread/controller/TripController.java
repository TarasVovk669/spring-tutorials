package com.virtualthread.controller;

import com.virtualthread.dto.FlightReservationRequest;
import com.virtualthread.dto.FlightReservationResponse;
import com.virtualthread.dto.TripPlan;
import com.virtualthread.service.PlanningService;
import com.virtualthread.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("trips")
@RequiredArgsConstructor
public class TripController {

  private static final Logger log = LoggerFactory.getLogger(TripController.class);
  private final PlanningService planningService;
  private final ReservationService reservationService;

  @GetMapping("/{code}")
  public TripPlan tripPlan(@PathVariable String code) {
    log.info("is_virtual: {}", Thread.currentThread().isVirtual());
    return planningService.getTripPlan(code);
  }

  @PostMapping
  public FlightReservationResponse reserve(@RequestBody FlightReservationRequest request) {
    log.info("is_virtual: {}", Thread.currentThread().isVirtual());
    return reservationService.reserve(request);
  }
}
