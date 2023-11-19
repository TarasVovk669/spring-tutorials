package com.tutorial.graphql.graphqltutorials.datasource.component.fake;

import com.netflix.graphql.dgs.*;
import com.tutorial.graphql.graphqltutorials.generated.DgsConstants;
import com.tutorial.graphql.graphqltutorials.generated.types.Hello;
import com.tutorial.graphql.graphqltutorials.generated.types.HelloInput;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

import static com.tutorial.graphql.graphqltutorials.fake.FakeHello.HELLO_LIST;

@Slf4j
@DgsComponent
public class FakeHelloDataResolver {

    @DgsQuery(field = DgsConstants.QUERY.AllHellos)
    public List<Hello> allHellos() {
        log.info("1111");
        log.info("222");
        log.info("3333");
        log.info("6666");
        log.info("7777");
        log.info("188888");
        return HELLO_LIST;
    }

    @DgsQuery
    public Hello oneHello() {
        return HELLO_LIST.get(ThreadLocalRandom.current().nextInt(HELLO_LIST.size()));
    }

    //@DgsQuery(field = DgsConstants.MUTATION.AddHello)
    @DgsMutation(field = DgsConstants.MUTATION.AddHello)
    public int addHello(@InputArgument(name = "helloInput") HelloInput input) {
        var hello = Hello.newBuilder()
                .text(input.getText())
                .randomNumber(input.getNumber())
                .build();

        HELLO_LIST.add(hello);

        return HELLO_LIST.size();
    }

    @DgsData(parentType = DgsConstants.MUTATION.TYPE_NAME, field = DgsConstants.MUTATION.ReplaceHello)
    public List<Hello> replaceHello(@InputArgument(name = "helloInput") HelloInput helloInput) {

        return HELLO_LIST.stream().map(h -> {
            if (h.getRandomNumber() == helloInput.getNumber()) {
                h.setText(helloInput.getText());
            }
            return h;
        }).collect(Collectors.toList());
    }

    @DgsData(parentType = DgsConstants.MUTATION.TYPE_NAME, field = DgsConstants.MUTATION.DeleteHello)
    public int deleteHello(Integer index) {
       HELLO_LIST.removeIf(h -> h.getRandomNumber().equals(index));

       return HELLO_LIST.size();
    }

}
