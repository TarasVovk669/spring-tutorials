package com.tutorial.graphql.graphqltutorials.service;

import com.tutorial.graphql.graphqltutorials.domain.Problemz;

import java.util.List;
import java.util.Optional;

public interface ProblemQueryService {

    List<Problemz> problemzLatestList();

    Optional<Problemz> getDetail(String id);

    Problemz createProblem(Problemz problem);

    List<Problemz> findAllByKeyword(String keyword);
}
