package com.tutorial.graphql.graphqltutorials.datasource.component.fake;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsData;
import com.tutorial.graphql.graphqltutorials.generated.DgsConstants;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@DgsComponent
public class FakeAdditionalDataResolver {

    @DgsData(parentType = DgsConstants.QUERY.TYPE_NAME, field = DgsConstants.QUERY.AdditionalDataRequest)
    public String additionalOnRequest(@RequestHeader(name = "optionalHeader", required = false) String optionalHeader,
                                      @RequestHeader(name = "mandatoryHeader") String mandatoryHeader,
                                      @RequestParam(name = "optionalParam", required = false) String optionalParam,
                                      @RequestParam(name = "mandatoryParam") String mandatoryParam) {

        return new StringBuilder().append("optionalHeader: ")
                .append(optionalHeader)
                .append(", mandatoryHeader: ")
                .append(mandatoryHeader)
                .append(", optionalParam: ")
                .append(optionalParam)
                .append(", mandatoryParam")
                .append(mandatoryParam)
                .toString();
    }


}
