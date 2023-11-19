package com.tutorial.graphql.graphqltutorials.util;

import com.tutorial.graphql.graphqltutorials.domain.Problemz;
import com.tutorial.graphql.graphqltutorials.domain.Solutionz;
import com.tutorial.graphql.graphqltutorials.domain.Userz;
import com.tutorial.graphql.graphqltutorials.domain.UserzToken;
import com.tutorial.graphql.graphqltutorials.generated.types.*;
import org.apache.commons.lang3.StringUtils;
import org.ocpsoft.prettytime.PrettyTime;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class GraphQLMapper {

    private static final PrettyTime PRETTY_TIME = new PrettyTime();
    private static final ZoneOffset ZONE_OFFSET = ZoneOffset.ofHours(2); //for EU

    public static User mapToGraphQL(Userz original) {

        return User.newBuilder()
                .id(original.getId().toString())
                .email(original.getEmail())
                .photo(original.getPhoto())
                .createDateTime(original.getCreationDateTime().atOffset(ZONE_OFFSET))
                .displayName(original.getDisplayName())
                .username(original.getUsername())
                .build();
    }

    public static Problem mapToGraphQL(Problemz original) {
        return Problem.newBuilder()
                .id(original.getId().toString())
                .author(mapToGraphQL(original.getCreatedBy()))
                .content(original.getContent())
                .createdDateTime(original.getCreatedDateTime().atOffset(ZONE_OFFSET))
                .prettyCreatedDateTime(PRETTY_TIME.format(original.getCreatedDateTime().atOffset(ZONE_OFFSET)))
                .solutionCount(original.getSolutions().size())
                .solutions(original.getSolutions().stream()
                        .sorted(Comparator.comparing(Solutionz::getCreatedDateTime).reversed())
                        .map(GraphQLMapper::mapToGraphQL
                        ).collect(Collectors.toList()))
                .tags(List.of(original.getTags().split(",")))
                .title(original.getTitle())
                .build();
    }

    public static Solution mapToGraphQL(Solutionz original) {
        return Solution.newBuilder()
                .id(original.getId().toString())
                .author(mapToGraphQL(original.getCreatedBy()))
                .category(StringUtils.equalsIgnoreCase(original.getCategory().name(), SolutionCategory.EXPLANATION.toString())
                        ? SolutionCategory.EXPLANATION
                        : SolutionCategory.REFERENCE)
                .content(original.getContent())
                .createdDateTime(original.getCreatedDateTime().atOffset(ZONE_OFFSET))
                .voteAsBadCount(original.getVoteAsBadCount())
                .voteAsGoodCount(original.getVoteAsGoodCount())
                .prettyCreatedDateTime(PRETTY_TIME.format(original.getCreatedDateTime().atOffset(ZONE_OFFSET)))
                .build();
    }

    public static UserAuthToken mapToGraphQL(UserzToken original) {
        return UserAuthToken.newBuilder()
                .authToken(original.getToken())
                .expiryTime(original.getExpirationDateTime().atOffset(ZONE_OFFSET))
                .build();
    }

    public static Problemz mapToEntity(ProblemCreateInput input, Userz author) {
        var p = new Problemz();
        p.setContent(input.getContent());
        p.setCreatedBy(author);
        p.setId(UUID.randomUUID());
        p.setSolutions(Collections.emptyList());
        p.setTags(String.join(",", input.getTags()));
        p.setTitle(input.getTitle());

        return p;
    }

    public static Solutionz mapToEntity(SolutionCreateInput input, Userz author, Problemz problemz) {
        return Solutionz.builder()
                .id(UUID.randomUUID())
                .problem(problemz)
                .createdBy(author)
                .category(input.getCategory())
                .content(input.getContent())
                .createdDateTime(LocalDateTime.now())
                .voteAsBadCount(0)
                .voteAsGoodCount(0)
                .build();
    }

    public static Userz mapToEntity(UserCreateInput input) {
        return Userz.builder()
                .id(UUID.randomUUID())
                .active(true)
                .creationDateTime(LocalDateTime.now())
                .email(input.getEmail())
                .password(HashUtil.hashBcrypt(input.getPassword()))
                .photo(input.getPhoto())
                .username(input.getUsername())
                .displayName(input.getDisplayName())
                .build();
    }
}
