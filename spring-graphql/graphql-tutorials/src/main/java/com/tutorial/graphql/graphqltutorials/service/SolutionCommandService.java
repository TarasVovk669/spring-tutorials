package com.tutorial.graphql.graphqltutorials.service;

import com.tutorial.graphql.graphqltutorials.domain.Solutionz;
import reactor.core.publisher.Flux;

import java.util.Optional;
import java.util.UUID;

public interface SolutionCommandService {

    Solutionz createSolution(Solutionz s);

    Optional<Solutionz> voteBad(UUID id);

    Optional<Solutionz> voteGood(UUID id);

    Flux<Solutionz> solutionzFlux();
}
