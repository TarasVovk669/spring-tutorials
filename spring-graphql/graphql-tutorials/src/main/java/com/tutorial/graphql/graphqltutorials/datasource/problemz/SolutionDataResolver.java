package com.tutorial.graphql.graphqltutorials.datasource.problemz;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsMutation;
import com.netflix.graphql.dgs.DgsSubscription;
import com.netflix.graphql.dgs.InputArgument;
import com.netflix.graphql.dgs.exceptions.DgsEntityNotFoundException;
import com.tutorial.graphql.graphqltutorials.domain.Solutionz;
import com.tutorial.graphql.graphqltutorials.exception.ProblemzAuthException;
import com.tutorial.graphql.graphqltutorials.generated.DgsConstants;
import com.tutorial.graphql.graphqltutorials.generated.types.Solution;
import com.tutorial.graphql.graphqltutorials.generated.types.SolutionCreateInput;
import com.tutorial.graphql.graphqltutorials.generated.types.SolutionResponse;
import com.tutorial.graphql.graphqltutorials.generated.types.SolutionVoteInput;
import com.tutorial.graphql.graphqltutorials.service.ProblemQueryService;
import com.tutorial.graphql.graphqltutorials.service.SolutionCommandService;
import com.tutorial.graphql.graphqltutorials.service.SolutionQueryService;
import com.tutorial.graphql.graphqltutorials.service.UserQueryService;
import com.tutorial.graphql.graphqltutorials.util.GraphQLMapper;
import org.springframework.web.bind.annotation.RequestHeader;
import reactor.core.publisher.Flux;

import java.util.Optional;
import java.util.UUID;

@DgsComponent
public class SolutionDataResolver {

    private final SolutionCommandService solutionCommandService;
    private final SolutionQueryService solutionQueryService;
    private final UserQueryService userQueryService;
    private final ProblemQueryService problemQueryService;

    public SolutionDataResolver(SolutionCommandService solutionCommandService, SolutionQueryService solutionQueryService, UserQueryService userQueryService, ProblemQueryService problemQueryService) {
        this.solutionCommandService = solutionCommandService;
        this.solutionQueryService = solutionQueryService;
        this.userQueryService = userQueryService;
        this.problemQueryService = problemQueryService;
    }

    @DgsMutation(field = DgsConstants.MUTATION.SolutionCreate)
    public SolutionResponse createSolution(
            @RequestHeader(name = "authToken") String token,
            @InputArgument(name = "solution") SolutionCreateInput input) {
        var userz = userQueryService.findUserzByToken(token).orElseThrow(ProblemzAuthException::new);
        var problemz = problemQueryService.getDetail(input.getProblemId()).orElseThrow(DgsEntityNotFoundException::new);
        var solutionz = solutionCommandService.createSolution(GraphQLMapper.mapToEntity(input, userz, problemz));

        return SolutionResponse.newBuilder().solution(GraphQLMapper.mapToGraphQL(solutionz)).build();

    }

    @DgsMutation(field = DgsConstants.MUTATION.SolutionVote)
    public SolutionResponse voteSolution(@RequestHeader(name = "authToken") String token,
                                         @InputArgument(name = "vote") SolutionVoteInput input) {
        Optional<Solutionz> updated;
        userQueryService.findUserzByToken(token).orElseThrow(ProblemzAuthException::new);

        if (input.getVoteAsGood()) {
            updated = solutionCommandService.voteGood(UUID.fromString(input.getSolutionId()));
        } else {
            updated = solutionCommandService.voteBad(UUID.fromString(input.getSolutionId()));
        }

        if (updated.isEmpty()) {
            throw new DgsEntityNotFoundException("Solution ".concat(input.getSolutionId()).concat(" not found"));
        }


        return SolutionResponse.newBuilder().solution(GraphQLMapper.mapToGraphQL(updated.get())).build();
    }

    @DgsSubscription(field = DgsConstants.SUBSCRIPTION.SolutionVoteChanged)
    public Flux<Solution> subscriptionSolutionVote(@InputArgument(name = "solutionId") String id) {
        return solutionCommandService.solutionzFlux().map(GraphQLMapper::mapToGraphQL);
    }

}
