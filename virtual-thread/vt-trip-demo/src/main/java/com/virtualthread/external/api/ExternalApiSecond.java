package com.virtualthread.external.api;

import com.virtualthread.dto.Flight;
import com.virtualthread.dto.FlightReservationRequest;
import com.virtualthread.dto.FlightReservationResponse;
import java.util.List;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.PostExchange;

public interface ExternalApiSecond {

    @PostExchange("/flight/reserve")
    FlightReservationResponse reserve(@RequestBody FlightReservationRequest request);


    @GetExchange("/flight/search/{departureCode}/{arrivalCode}")
    List<Flight> search(@PathVariable String departureCode,
                        @PathVariable String arrivalCode);

}
