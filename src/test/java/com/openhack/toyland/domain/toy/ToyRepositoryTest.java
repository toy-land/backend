package com.openhack.toyland.domain.toy;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.openhack.toyland.domain.Organization;
import com.openhack.toyland.domain.OrganizationRepository;

@SpringBootTest
class ToyRepositoryTest {

    @Autowired
    private ToyRepository toyRepository;

    @Autowired
    private OrganizationRepository organizationRepository;

    @Test
    void existsByGithubIdentifier() {
        organizationRepository.save(new Organization("SOPT"));

        Toy toy = new Toy(123L, "title", "password", "description", "readme", "logoUrl", "githubLink", "serviceLink",
            "email", 1L, "AI", "LESS_THAN_A_DAY", null);

        toyRepository.save(toy);

        assertThat(toyRepository.existsByGithubIdentifier(toy.getGithubIdentifier())).isTrue();
    }
}
