package com.openhack.toyland.domain.user;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void updateUsername() {
        User user = User.builder()
            .username("toneyparky")
            .githubIdentifier(123123L)
            .avatarUrl("url")
            .build();

        User saved = userRepository.save(user);

        User changed = User.builder()
            .username("toney")
            .githubIdentifier(123123L)
            .avatarUrl("url")
            .build();

        userRepository.updateUsername(changed.getUsername(), changed.getGithubIdentifier());

        assertThat(userRepository.findById(saved.getId()).get().getGithubIdentifier())
            .isEqualTo(user.getGithubIdentifier());
    }
}