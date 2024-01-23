package com.virtualthread.dto;

import java.time.LocalDate;

public record TripReservationRequest(String departure, String arrival, LocalDate date) {}
