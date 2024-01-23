package com.virtualthread.external.api;

import com.virtualthread.dto.*;
import java.util.List;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.service.annotation.GetExchange;

public interface ExternalApiOne {

  @GetExchange("/accommodations/{airportCode}")
  List<Accommodation> getAccommodations(@PathVariable(name = "airportCode") String code);

  @GetExchange("/events/{airportCode}")
  List<Event> getEvents(@PathVariable(name = "airportCode") String code);

  @GetExchange("/local-recommendations/{airportCode}")
  LocalRecommendations getRecommendation(@PathVariable(name = "airportCode") String code);

  @GetExchange("/transportation/{airportCode}")
  Transportation getTransportation(@PathVariable(name = "airportCode") String code);

  @GetExchange("/weather/{airportCode}")
  Weather getWeather(@PathVariable(name = "airportCode") String code);
}
