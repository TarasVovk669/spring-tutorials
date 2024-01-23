package com.virtualthread.service;

import com.virtualthread.dto.FlightReservationRequest;
import com.virtualthread.dto.FlightReservationResponse;

public interface ReservationService {
    FlightReservationResponse reserve(FlightReservationRequest request);
}
