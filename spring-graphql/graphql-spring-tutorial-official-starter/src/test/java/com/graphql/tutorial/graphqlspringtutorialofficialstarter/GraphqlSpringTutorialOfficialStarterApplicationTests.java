package com.graphql.tutorial.graphqlspringtutorialofficialstarter;

import com.graphql.spring.boot.test.GraphQLTestTemplate;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.json.JSONException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

import static org.skyscreamer.jsonassert.JSONAssert.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = GraphqlSpringTutorialOfficialStarterApplication.class)
class GraphqlSpringTutorialOfficialStarterApplicationTests {

    private String REQUEST_PATH = "data-mock/%s.graphql";
    private String RESPONSE_PATH = "data-mock/%s.json";

    @Autowired
    private GraphQLTestTemplate graphQLTestTemplate;

    @Test
    void checkBankAccount() throws IOException, JSONException {
        var testName = "bankAccount";
        var response = graphQLTestTemplate.postForResource(String.format(REQUEST_PATH,testName));
        var expectedRespBody = read(String.format(RESPONSE_PATH,testName));

        Assertions.assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertEquals(expectedRespBody, response.getRawResponse().getBody(), false);
    }

    private String read(String path) throws IOException {
        InputStream inputStream = new ClassPathResource(path).getInputStream();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
            return reader.lines().collect(Collectors.joining(System.lineSeparator()));
        }
    }



}
