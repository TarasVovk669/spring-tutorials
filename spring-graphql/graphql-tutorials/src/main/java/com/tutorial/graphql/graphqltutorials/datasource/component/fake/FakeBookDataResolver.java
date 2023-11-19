package com.tutorial.graphql.graphqltutorials.datasource.component.fake;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsQuery;
import com.netflix.graphql.dgs.InputArgument;
import com.tutorial.graphql.graphqltutorials.generated.DgsConstants;
import com.tutorial.graphql.graphqltutorials.generated.types.Book;
import com.tutorial.graphql.graphqltutorials.generated.types.ReleaseHistory;
import com.tutorial.graphql.graphqltutorials.generated.types.ReleaseHistoryInput;
import graphql.schema.DataFetchingEnvironment;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.tutorial.graphql.graphqltutorials.fake.FakeBook.BOOKS_LIST;
import static org.apache.commons.lang3.StringUtils.isBlank;

@DgsComponent
public class FakeBookDataResolver {

    @DgsQuery(field = "books")
    public List<Book> getAllBooksBy(@InputArgument(name = "author") Optional<String> authorName) {
        if (authorName.isEmpty() || isBlank(authorName.get())) {
            return BOOKS_LIST;
        }

        return BOOKS_LIST.stream()
                .filter(book -> book.getAuthor().getName().equalsIgnoreCase(authorName.get()))
                .collect(Collectors.toList());
    }

    @DgsQuery(field = "booksByRelesed")
    public List<Book> getBooksByRelesed(DataFetchingEnvironment dataFetchingEnvironment) {
        var input = (Map<String, Object>) dataFetchingEnvironment.getArgument("inputReleased");

        var request = ReleaseHistoryInput.newBuilder()
                .printedEdition((boolean) input.get(DgsConstants.RELEASEHISTORYINPUT.PrintedEdition))
                .year((int) input.get(DgsConstants.RELEASEHISTORYINPUT.Year))
                .build();
        return BOOKS_LIST.stream().filter(x -> matchInputs(request, x.getReleased())).collect(Collectors.toList());
    }

    private static boolean matchInputs(ReleaseHistoryInput input, ReleaseHistory history) {
        return input.getPrintedEdition().equals(history.getPrintedEdition())
                && input.getYear().equals(history.getYear());
    }

}
