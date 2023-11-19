package com.tutorial.graphql.graphqltutorials.datasource.component.fake;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsQuery;
import com.netflix.graphql.dgs.InputArgument;
import com.tutorial.graphql.graphqltutorials.generated.types.MobileApp;
import com.tutorial.graphql.graphqltutorials.generated.types.MobileAppFilter;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.tutorial.graphql.graphqltutorials.fake.FakeMobileApp.MOBILE_APPS_LIST;
import static org.apache.commons.lang3.StringUtils.EMPTY;

@DgsComponent
public class FakeMobileAppDataResolver {

    @DgsQuery(field = "mobileApps")
    public List<MobileApp> getMobileApps(@InputArgument(name = "mobileAppFilter") Optional<MobileAppFilter> filter) {

        if (filter.isEmpty()) {
            return MOBILE_APPS_LIST;
        }


        return MOBILE_APPS_LIST.stream().filter(mobileApp ->
                this.matchFiler(filter.get(), mobileApp)
        ).collect(Collectors.toList());
    }

    private boolean matchFiler(MobileAppFilter filter, MobileApp app) {
        var isAppMatch = StringUtils.containsIgnoreCase(app.getName(),
                StringUtils.defaultIfBlank(filter.getName(), StringUtils.EMPTY))
                && StringUtils.containsIgnoreCase(app.getVersion(),
                StringUtils.defaultIfBlank(filter.getVersion(), StringUtils.EMPTY))
                && app.getReleaseDate().isAfter(Optional.ofNullable(filter.getReleasedAfter()).orElse(LocalDate.MIN))
                && app.getDownload() >= Optional.ofNullable(filter.getMinDownload()).orElse(0);

        if (!isAppMatch) {
            return false;
        }

        if (StringUtils.isNotBlank(filter.getPlatform())
                && !app.getPlatform().contains(filter.getPlatform())) {
            return false;
        }

        if (null != filter.getAuthor()
                && !StringUtils.containsIgnoreCase(app.getAuthor().getName(),
                StringUtils.defaultIfBlank(filter.getAuthor().getName(), EMPTY))) {
            return false;
        }

        if (null != filter.getCategory()
                && !app.getCategory().equals(filter.getCategory())) {
            return false;
        }

        return true;
    }

}
