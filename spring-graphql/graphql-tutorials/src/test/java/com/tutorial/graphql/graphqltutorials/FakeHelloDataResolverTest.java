package com.tutorial.graphql.graphqltutorials;

import com.netflix.graphql.dgs.DgsQueryExecutor;
import org.intellij.lang.annotations.Language;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class FakeHelloDataResolverTest {

    @Autowired
    private DgsQueryExecutor executor;

    @Test
    public void testOneHello(){
        @Language("GraphQL") var oneHelloQuery = """
                {
                    oneHello {
                     text
                     randomNumber
                    }
                }
                """;

        String text = executor.executeAndExtractJsonPath(
                oneHelloQuery, "data.oneHello.text");
        Integer randomNumber = executor.executeAndExtractJsonPath(
                oneHelloQuery, "data.oneHello.randomNumber");

        assertNotNull(text);
        assertNotNull(randomNumber);
        assertFalse(isBlank(text));
    }

    @Test
    public void testAllHello(){
        @Language("GraphQL") var allHelloQuery = """
                {
                    allHellos {
                     text
                     randomNumber
                    }
                }
                """;

        List<String> texts = executor.executeAndExtractJsonPath(
                allHelloQuery, "data.allHellos[*].text");
        List<Integer> randomNumber = executor.executeAndExtractJsonPath(
                allHelloQuery, "data.allHellos[*].randomNumber");

        assertNotNull(texts);
        assertNotNull(randomNumber);
        assertFalse(texts.isEmpty());
    }
}
