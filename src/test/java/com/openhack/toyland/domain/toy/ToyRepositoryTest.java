package com.openhack.toyland.domain.toy;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.openhack.toyland.domain.Organization;
import com.openhack.toyland.domain.OrganizationRepository;

@DataJpaTest
class ToyRepositoryTest {

    @Autowired
    private ToyRepository toyRepository;

    @Autowired
    private OrganizationRepository organizationRepository;

    private Toy toy;

    @BeforeEach
    void setUp() {
        toy = new Toy(null, 123L, "title", "password", "description", "readme", "logoUrl", "githubLink", "serviceLink",
            "email", 1L, "AI", "LESS_THAN_A_DAY");
    }

    @Test
    void existsByGithubIdentifier() {
        organizationRepository.save(new Organization("SOPT"));

        toyRepository.save(toy);

        assertThat(toyRepository.existsByGithubIdentifier(toy.getGithubIdentifier())).isTrue();
    }

    @DisplayName("이메일이 null과 빈 문자열이 아닌 객체를 조회한다")
    @Test
    void findByIdAndEmailIsNotNullAndEmailNot() {
        Toy saved = toyRepository.save(toy);

        Toy emptyEmailToy = new Toy(null, 456L, "title", "password", "description", "readme", "logoUrl", "githubLink",
            "serviceLink", "", 1L, "AI", "LESS_THAN_A_DAY");
        Toy nullEmailToy = new Toy(null, 789L, "title", "password", "description", "readme", "logoUrl", "githubLink",
            "serviceLink", null, 1L, "AI", "LESS_THAN_A_DAY");

        Toy savedEmptyEmail = toyRepository.save(emptyEmailToy);
        Toy savedNullEmail = toyRepository.save(nullEmailToy);

        assertAll(
            () -> assertThat(toyRepository.findByIdAndEmailIsNotNullAndEmailNot(saved.getId(), "").get()).isEqualTo(
                saved),
            () -> assertThat(
                toyRepository.findByIdAndEmailIsNotNullAndEmailNot(savedEmptyEmail.getId(), "")).isNotPresent(),
            () -> assertThat(
                toyRepository.findByIdAndEmailIsNotNullAndEmailNot(savedNullEmail.getId(), "")).isNotPresent()
        );
    }
}
