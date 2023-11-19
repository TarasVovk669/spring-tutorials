package com.tutorial.graphql.graphqltutorials.service;

import com.tutorial.graphql.graphqltutorials.domain.Solutionz;

import java.util.List;

public interface SolutionQueryService {
    List<Solutionz> findAllByKeyword(String keyword);
}
