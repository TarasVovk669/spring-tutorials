package com.tutorial.graphql.graphqltutorials.datasource.component.fake;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsQuery;
import com.netflix.graphql.dgs.InputArgument;
import com.tutorial.graphql.graphqltutorials.generated.DgsConstants;
import com.tutorial.graphql.graphqltutorials.generated.types.SmartSearchResult;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.tutorial.graphql.graphqltutorials.fake.FakeBook.BOOKS_LIST;
import static com.tutorial.graphql.graphqltutorials.fake.FakeHello.HELLO_LIST;

@DgsComponent
public class FakeSmartSearchResultResolver {

    @DgsQuery(field = DgsConstants.QUERY.SmartSearch)
    public List<SmartSearchResult> smartSearch(@InputArgument(name = "keyword") Optional<String> keyword) {
        final List<SmartSearchResult> smartSearchResults = new ArrayList<>();

        if (keyword.isEmpty()) {
            smartSearchResults.addAll(HELLO_LIST);
            smartSearchResults.addAll(BOOKS_LIST);
        }else {
            smartSearchResults.addAll(HELLO_LIST.stream()
                            .filter(h -> StringUtils.containsIgnoreCase(h.getText(),keyword.get()))
                    .toList());

            smartSearchResults.addAll(BOOKS_LIST.stream()
                    .filter(b -> StringUtils.containsIgnoreCase(b.getTitle(),keyword.get()))
                    .toList());
        }

        return smartSearchResults;
    }
}
