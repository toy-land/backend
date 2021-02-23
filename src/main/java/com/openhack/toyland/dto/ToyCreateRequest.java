package com.openhack.toyland.dto;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.openhack.toyland.domain.toy.Period;
import com.openhack.toyland.domain.toy.Toy;
import com.openhack.toyland.domain.user.User;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PUBLIC)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class ToyCreateRequest {
    @NotNull
    private Long githubIdentifier;

    @NotEmpty
    private String title;

    @NotEmpty
    private String password;

    @NotEmpty
    private String description;

    private String readme;

    private String logoUrl;

    @NotEmpty
    private String githubLink;

    private String serviceLink;

    private String email;

    @NotNull
    private Long organizationId;

    @NotNull
    private String category;

    @NotNull
    private Long period;

    private String pushedAt;

    @NotEmpty
    private List<ContributorCreateRequest> contributors;

    @NotEmpty
    private List<Long> techStackIds;

    public Toy toEntity() {
        return Toy.builder()
            .githubIdentifier(githubIdentifier)
            .title(title)
            .password(password)
            .description(description)
            .readme(readme)
            .logoUrl(logoUrl)
            .githubLink(githubLink)
            .serviceLink(serviceLink)
            .email(email)
            .organizationId(organizationId)
            .category(category)
            .period(Period.of(period).toString())
            .build();
    }

    public List<User> toUsers() {
        return contributors.stream()
            .map(ContributorCreateRequest::toUser)
            .collect(Collectors.toList());
    }
}
