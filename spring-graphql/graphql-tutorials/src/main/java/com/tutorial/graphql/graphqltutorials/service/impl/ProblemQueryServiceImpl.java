package com.tutorial.graphql.graphqltutorials.service.impl;

import com.tutorial.graphql.graphqltutorials.dao.ProblemzRepository;
import com.tutorial.graphql.graphqltutorials.domain.Problemz;
import com.tutorial.graphql.graphqltutorials.service.ProblemQueryService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProblemQueryServiceImpl implements ProblemQueryService {

    private final ProblemzRepository problemzRepository;

    public ProblemQueryServiceImpl(ProblemzRepository problemzRepository) {
        this.problemzRepository = problemzRepository;
    }

    public List<Problemz> problemzLatestList(){
        return problemzRepository.findAllByOrderByCreatedDateTimeDesc();
    }

    @Override
    public Optional<Problemz> getDetail(String id) {
        return problemzRepository.findById(UUID.fromString(id));
    }

    @Override
    public Problemz createProblem(Problemz problem) {
        return problemzRepository.save(problem);
    }

    @Override
    public List<Problemz> findAllByKeyword(String keyword){
        return problemzRepository.findByKey("%".concat(keyword).concat("%"));
    }
}
