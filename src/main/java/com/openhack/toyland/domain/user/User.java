package com.openhack.toyland.domain.user;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.validation.constraints.NotNull;

import com.openhack.toyland.domain.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class User extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private Long githubIdentifier;

    @NotNull
    private String username;

    @Lob
    private String avatarUrl;

    @Builder
    public User(Long githubIdentifier, String username, String avatarUrl) {
        this.id = null;
        this.githubIdentifier = githubIdentifier;
        this.username = username;
        this.avatarUrl = avatarUrl;
    }

    public boolean isSameGithubIdentifier(User user) {
        return githubIdentifier.equals(user.getGithubIdentifier());
    }
}
