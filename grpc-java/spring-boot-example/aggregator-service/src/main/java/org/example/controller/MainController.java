package org.example.controller;

import com.grpc.example.common.Genre;
import com.grpc.example.user.UserGenreUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.example.dto.RecommendationDto;
import org.example.dto.UserDto;
import org.example.service.GrpcClientService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class MainController {

    private final GrpcClientService clientService;

    @GetMapping("/recommendations/{id}")
    public RecommendationDto getRecommendations(@PathVariable Long id) {
        return clientService.getRecommendations(id);
    }

    @PutMapping("/users/{id}")
    public UserDto getRecommendations(@PathVariable Long id, @RequestParam(name = "genre") Genre genre) {
        return clientService.changeUserGenre(id,genre);
    }
}
