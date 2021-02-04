package com.openhack.toyland.dto;

import com.openhack.toyland.domain.user.User;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class UserResponse {
    private Long id;
    private String username;
    private String avatarName;

    public UserResponse(Long id, String username, String avatarName) {
        this.id = id;
        this.username = username;
        this.avatarName = avatarName;
    }

    public static UserResponse from(User user) {
        return new UserResponse(user.getId(), user.getUsername(), user.getAvatarUrl());
    }
}
