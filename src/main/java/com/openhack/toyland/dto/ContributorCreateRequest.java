package com.openhack.toyland.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.openhack.toyland.domain.user.User;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class ContributorCreateRequest {
    @NotNull
    private Long githubIdentifier;

    @NotEmpty
    private String username;

    private String avatarUrl;

    public User toUser() {
        return User.builder()
            .githubIdentifier(githubIdentifier)
            .username(username)
            .avatarUrl(avatarUrl)
            .build();
    }
}
