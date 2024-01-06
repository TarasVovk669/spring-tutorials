package org.example.dto;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDate;

@Data
@ToString
@Builder
public class Movie {

    private String title;
    private LocalDate releaseDate;
    private Double rating;
}
