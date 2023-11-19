package com.tutorial.graphql.graphqltutorials.service;

import com.tutorial.graphql.graphqltutorials.domain.Problemz;
import reactor.core.publisher.Flux;

public interface ProblemzCommandService {

    Problemz createProblem(Problemz p);

    Flux<Problemz> problemFlux();
}
