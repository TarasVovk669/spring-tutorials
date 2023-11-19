package com.tutorial.graphql.graphqltutorials.service.impl;

import com.tutorial.graphql.graphqltutorials.dao.ProblemzRepository;
import com.tutorial.graphql.graphqltutorials.domain.Problemz;
import com.tutorial.graphql.graphqltutorials.service.ProblemzCommandService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

@Service
public class ProblemzCommandServiceImpl implements ProblemzCommandService {

    private Sinks.Many<Problemz> problemzSink = Sinks.many().multicast().onBackpressureBuffer();

    private final ProblemzRepository problemzRepository;

    public ProblemzCommandServiceImpl(ProblemzRepository problemzRepository) {
        this.problemzRepository = problemzRepository;
    }

    @Override
    public Problemz createProblem(Problemz p) {
        var problem = problemzRepository.save(p);

        problemzSink.tryEmitNext(problem);
        return problem;
    }

    @Override
    public Flux<Problemz> problemFlux() {
        return problemzSink.asFlux();
    }

}
