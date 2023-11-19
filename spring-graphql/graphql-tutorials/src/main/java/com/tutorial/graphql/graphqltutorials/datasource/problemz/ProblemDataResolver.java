package com.tutorial.graphql.graphqltutorials.datasource.problemz;

import com.netflix.graphql.dgs.*;
import com.netflix.graphql.dgs.exceptions.DgsEntityNotFoundException;
import com.tutorial.graphql.graphqltutorials.exception.ProblemzAuthException;
import com.tutorial.graphql.graphqltutorials.generated.DgsConstants;
import com.tutorial.graphql.graphqltutorials.generated.types.Problem;
import com.tutorial.graphql.graphqltutorials.generated.types.ProblemCreateInput;
import com.tutorial.graphql.graphqltutorials.generated.types.ProblemResponse;
import com.tutorial.graphql.graphqltutorials.service.ProblemQueryService;
import com.tutorial.graphql.graphqltutorials.service.ProblemzCommandService;
import com.tutorial.graphql.graphqltutorials.service.UserQueryService;
import com.tutorial.graphql.graphqltutorials.util.GraphQLMapper;
import org.springframework.web.bind.annotation.RequestHeader;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.stream.Collectors;

@DgsComponent
public class ProblemDataResolver {

    private final ProblemQueryService problemQueryService;
    private final ProblemzCommandService problemzCommandService;
    private final UserQueryService userQueryService;

    public ProblemDataResolver(ProblemQueryService problemQueryService, ProblemzCommandService problemzCommandService, UserQueryService userQueryService) {
        this.problemQueryService = problemQueryService;
        this.problemzCommandService = problemzCommandService;
        this.userQueryService = userQueryService;
    }

    @DgsQuery(field = DgsConstants.QUERY.ProblemLatestList)
    public List<Problem> getLatestProblem() {
        return problemQueryService.problemzLatestList().stream()
                .map(GraphQLMapper::mapToGraphQL).collect(Collectors.toList());
    }

    @DgsQuery(field = DgsConstants.QUERY.ProblemDetail)
    public Problem getProblemDetail(@InputArgument(name = "id") String problemId) {
        return problemQueryService.getDetail(problemId).map(GraphQLMapper::mapToGraphQL).orElseThrow(DgsEntityNotFoundException::new);
    }

    @DgsMutation(field = DgsConstants.MUTATION.ProblemCreate)
    public ProblemResponse createProblem(@RequestHeader(name = "authToken") String authToken,
                                         @InputArgument(name = "problem") ProblemCreateInput input) {

        var userz = userQueryService.findUserzByToken(authToken).orElseThrow(ProblemzAuthException::new);
        var problemz = problemzCommandService.createProblem(GraphQLMapper.mapToEntity(input,userz));

        return ProblemResponse.newBuilder().problem(GraphQLMapper.mapToGraphQL(problemz)).build();
    }

    @DgsSubscription(field = DgsConstants.SUBSCRIPTION.ProblemAdded)
    public Flux<Problem> subscribeProblem() {
        return problemzCommandService.problemFlux().map(GraphQLMapper::mapToGraphQL);
    }


}
