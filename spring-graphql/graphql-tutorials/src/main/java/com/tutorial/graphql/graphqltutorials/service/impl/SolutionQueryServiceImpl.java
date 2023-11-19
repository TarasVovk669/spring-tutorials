package com.tutorial.graphql.graphqltutorials.service.impl;

import com.tutorial.graphql.graphqltutorials.dao.SolutionzRepository;
import com.tutorial.graphql.graphqltutorials.domain.Solutionz;
import com.tutorial.graphql.graphqltutorials.service.SolutionQueryService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SolutionQueryServiceImpl implements SolutionQueryService {

    private final SolutionzRepository repository;

    public SolutionQueryServiceImpl(SolutionzRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Solutionz> findAllByKeyword(String keyword){
        return repository.findByKey("%".concat(keyword).concat("%"));
    }
}
