package org.example.dto;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@ToString
@Builder
public class RecommendationDto {

    private UserDto user;
    private List<Movie> movies;
}
