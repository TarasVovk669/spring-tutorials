package com.tutorial.graphql.graphqltutorials.datasource.problemz;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsQuery;
import com.netflix.graphql.dgs.InputArgument;
import com.netflix.graphql.dgs.exceptions.DgsEntityNotFoundException;
import com.tutorial.graphql.graphqltutorials.generated.DgsConstants;
import com.tutorial.graphql.graphqltutorials.generated.types.SearchItemFilter;
import com.tutorial.graphql.graphqltutorials.generated.types.SearchableItem;
import com.tutorial.graphql.graphqltutorials.service.ProblemQueryService;
import com.tutorial.graphql.graphqltutorials.service.SolutionQueryService;
import com.tutorial.graphql.graphqltutorials.util.GraphQLMapper;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@DgsComponent
public class ItemSearchDataResolver {

    private final ProblemQueryService problemQueryService;
    private final SolutionQueryService solutionQueryService;

    public ItemSearchDataResolver(ProblemQueryService problemQueryService, SolutionQueryService solutionQueryService) {
        this.problemQueryService = problemQueryService;
        this.solutionQueryService = solutionQueryService;
    }


    @DgsQuery(field = DgsConstants.QUERY.ItemSearch)
    public List<SearchableItem> search(@InputArgument(name = "filter")SearchItemFilter filter){
        var result = new ArrayList<SearchableItem>();
        var keyword = filter.getKeyword();

        var problems = problemQueryService.findAllByKeyword(keyword).stream().map(GraphQLMapper::mapToGraphQL)
                .collect(Collectors.toList());

        var solutions = solutionQueryService.findAllByKeyword(keyword).stream().map(GraphQLMapper::mapToGraphQL)
                .collect(Collectors.toList());

        result.addAll(problems);
        result.addAll(solutions);

        if(result.isEmpty()){
            throw new DgsEntityNotFoundException("No items with key: "+ keyword);
        }

        result.sort(Comparator.comparing(SearchableItem::getCreatedDateTime));
        return result;
    }
}
