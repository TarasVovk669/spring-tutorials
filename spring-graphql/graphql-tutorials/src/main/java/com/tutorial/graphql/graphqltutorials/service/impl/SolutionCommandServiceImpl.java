package com.tutorial.graphql.graphqltutorials.service.impl;

import com.tutorial.graphql.graphqltutorials.dao.SolutionzRepository;
import com.tutorial.graphql.graphqltutorials.domain.Solutionz;
import com.tutorial.graphql.graphqltutorials.service.SolutionCommandService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

import java.util.Optional;
import java.util.UUID;

@Service
public class SolutionCommandServiceImpl implements SolutionCommandService {

    private final SolutionzRepository repository;

    private Sinks.Many<Solutionz> solutionzSink = Sinks.many().multicast().onBackpressureBuffer();

    public SolutionCommandServiceImpl(SolutionzRepository repository) {
        this.repository = repository;
    }

    @Override
    public Solutionz createSolution(Solutionz s) {
        return repository.save(s);
    }

    @Override
    public Optional<Solutionz> voteBad(UUID id) {
        repository.addVoteBadCount(id);

        var solution = repository.findById(id);
        if (solution.isPresent()) {
            solutionzSink.tryEmitNext(solution.get());
        }

        return solution;
    }

    @Override
    public Optional<Solutionz> voteGood(UUID id) {
        repository.addVoteGoodCount(id);
        var solution = repository.findById(id);

        if (solution.isPresent()) {
            solutionzSink.tryEmitNext(solution.get());
        }

        return solution;
    }

    @Override
    public Flux<Solutionz> solutionzFlux(){
        return solutionzSink.asFlux();
    }
}