package com.openhack.toyland.service.toy;

import static org.assertj.core.api.AssertionsForClassTypes.*;

import java.util.Collections;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.openhack.toyland.PersistenceTest;
import com.openhack.toyland.domain.toy.Period;
import com.openhack.toyland.domain.toy.Toy;
import com.openhack.toyland.domain.toy.ToyRepository;
import com.openhack.toyland.dto.ContributorCreateRequest;
import com.openhack.toyland.dto.UpdateToyRequestBody;
import com.openhack.toyland.exception.EntityNotFoundException;
import com.openhack.toyland.exception.InvalidRequestBodyException;
import com.openhack.toyland.exception.UnAuthorizedEventException;

public class ToyUpdateServiceTest extends PersistenceTest {

    private final long toyId = 5L;

    @Autowired
    private ToyRepository toyRepository;

    @Autowired
    private ToyService toyService;

    @DisplayName("toy update 성공 테스트")
    @Test
    void updateById() {
        Toy originalToy = toyRepository.findById(toyId).get();

        ContributorCreateRequest contributor = new ContributorCreateRequest(123L, "toneyparky", "https://id.com");

        UpdateToyRequestBody updateToyRequestBody = new UpdateToyRequestBody(toyId, 139023480L, "BackendServer",
            "toytoytoy",
            "2018 오픈핵 백엔드 서버 레파지토리", "readme", "https://avatars.githubusercontent.com/u/40666007?v=4",
            "https://github.com/TeampleStay/BackendServer", "", "", 5L, "ANDROID", 0L,
            "2021-02-06 04:13:56.711720", Collections.singletonList(contributor), Collections.singletonList(1L));

        toyService.updateById(toyId, updateToyRequestBody);
        Toy updatedToy = toyRepository.findById(toyId).get();

        Assertions.assertAll(
            () -> Assertions.assertEquals(originalToy.getId(), updatedToy.getId()),
            () -> Assertions.assertEquals(updateToyRequestBody.getGithubIdentifier(), updatedToy.getGithubIdentifier()),
            () -> Assertions.assertEquals(updateToyRequestBody.getCategory(), updatedToy.getCategory().name()),
            () -> Assertions.assertEquals(updateToyRequestBody.getDescription(), updatedToy.getDescription()),
            () -> Assertions.assertEquals(updateToyRequestBody.getEmail(), updatedToy.getEmail()),
            () -> Assertions.assertEquals(updateToyRequestBody.getGithubLink(), updatedToy.getGithubLink()),
            () -> Assertions.assertEquals(updateToyRequestBody.getLogoUrl(), updatedToy.getLogoUrl()),
            () -> Assertions.assertEquals(updateToyRequestBody.getOrganizationId(), updatedToy.getOrganizationId()),
            () -> Assertions.assertEquals(updateToyRequestBody.getPassword(), updatedToy.getPassword()),
            () -> Assertions.assertEquals(Period.of(updateToyRequestBody.getPeriod()), updatedToy.getPeriod()),
            () -> Assertions.assertEquals(updateToyRequestBody.getReadme(), updatedToy.getReadme()),
            () -> Assertions.assertEquals(updateToyRequestBody.getTitle(), updatedToy.getTitle()),
            () -> Assertions.assertEquals(updateToyRequestBody.getServiceLink(), updatedToy.getServiceLink())
        );
    }

    @DisplayName("toy update 실패 테스트 - toy id 없음 오류")
    @Test
    void updateById_WhenInvalidToyId_ThrowException() {
        ContributorCreateRequest contributor = new ContributorCreateRequest(123L, "toneyparky", "https://id.com");

        UpdateToyRequestBody updateToyRequestBody = new UpdateToyRequestBody(100L, 139023480L, "BackendServer",
            "toytoytoy",
            "2018 오픈핵 백엔드 서버 레파지토리", "readme", "https://avatars.githubusercontent.com/u/40666007?v=4",
            "https://github.com/TeampleStay/BackendServer", "", "", 5L, "ANDROID", 0L,
            "2021-02-06 04:13:56.711720", Collections.singletonList(contributor), Collections.singletonList(1L));

        assertThatThrownBy(() -> {
            toyService.updateById(100L, updateToyRequestBody);
        }).isInstanceOf(EntityNotFoundException.class);
    }

    @DisplayName("toy update 실패 테스트 - Organization id 없음 오류")
    @Test
    void updateById_WhenInvalidOrganizationId_ThrowException() {
        ContributorCreateRequest contributor = new ContributorCreateRequest(123L, "toneyparky", "https://id.com");

        UpdateToyRequestBody updateToyRequestBody = new UpdateToyRequestBody(toyId, 139023480L, "BackendServer",
            "toytoytoy",
            "2018 오픈핵 백엔드 서버 레파지토리", "readme", "https://avatars.githubusercontent.com/u/40666007?v=4",
            "https://github.com/TeampleStay/BackendServer", "", "", 100L, "ANDROID", 0L,
            "2021-02-06 04:13:56.711720", Collections.singletonList(contributor), Collections.singletonList(1L));

        assertThatThrownBy(() -> {
            toyService.updateById(toyId, updateToyRequestBody);
        }).isInstanceOf(EntityNotFoundException.class);
    }

    @DisplayName("toy update 실패 테스트 - Category 없음 오류")
    @Test
    void updateById_WhenInvalidCategory_ThrowException() {
        ContributorCreateRequest contributor = new ContributorCreateRequest(123L, "toneyparky", "https://id.com");

        UpdateToyRequestBody updateToyRequestBody = new UpdateToyRequestBody(toyId, 139023480L, "BackendServer",
            "toytoytoy",
            "2018 오픈핵 백엔드 서버 레파지토리", "readme", "https://avatars.githubusercontent.com/u/40666007?v=4",
            "https://github.com/TeampleStay/BackendServer", "", "", 5L, "ANDROIDS", 0L,
            "2021-02-06 04:13:56.711720", Collections.singletonList(contributor), Collections.singletonList(1L));

        assertThatThrownBy(() -> {
            toyService.updateById(toyId, updateToyRequestBody);
        }).isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("toy update 실패 테스트 - Period 없음 오류")
    @Test
    void updateById_whenInvalidPeriod_ThrowException() {
        ContributorCreateRequest contributor = new ContributorCreateRequest(123L, "toneyparky", "https://id.com");

        UpdateToyRequestBody updateToyRequestBody = new UpdateToyRequestBody(toyId, 139023480L, "BackendServer",
            "toytoytoy",
            "2018 오픈핵 백엔드 서버 레파지토리", "readme", "https://avatars.githubusercontent.com/u/40666007?v=4",
            "https://github.com/TeampleStay/BackendServer", "", "", 5L, "ANDROID", 100L,
            "2021-02-06 04:13:56.711720", Collections.singletonList(contributor), Collections.singletonList(1L));

        assertThatThrownBy(() -> {
            toyService.updateById(toyId, updateToyRequestBody);
        }).isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("toy update 실패 테스트 - 비밀번호 오류")
    @Test
    void updateById_WhenInvalidPassword_ThrowException() {
        ContributorCreateRequest contributor = new ContributorCreateRequest(123L, "toneyparky", "https://id.com");

        UpdateToyRequestBody updateToyRequestBody = new UpdateToyRequestBody(toyId, 139023480L, "BackendServer",
            "toy",
            "2018 오픈핵 백엔드 서버 레파지토리", "readme", "https://avatars.githubusercontent.com/u/40666007?v=4",
            "https://github.com/TeampleStay/BackendServer", "", "", 5L, "ANDROID", 0L,
            "2021-02-06 04:13:56.711720", Collections.singletonList(contributor), Collections.singletonList(1L));

        assertThatThrownBy(() -> {
            toyService.updateById(toyId, updateToyRequestBody);
        }).isInstanceOf(UnAuthorizedEventException.class);
    }

    @DisplayName("toy update 실패 테스트 - 수정된 GithubIdentifier 오류")
    @Test
    void updateById_WhenDifferentGithubIdentifier_ThrowException() {
        ContributorCreateRequest contributor = new ContributorCreateRequest(123L, "toneyparky", "https://id.com");

        UpdateToyRequestBody updateToyRequestBody = new UpdateToyRequestBody(toyId, 123L, "BackendServer",
            "toytoytoy",
            "2018 오픈핵 백엔드 서버 레파지토리", "readme", "https://avatars.githubusercontent.com/u/40666007?v=4",
            "https://github.com/TeampleStay/BackendServer", "", "", 5L, "ANDROID", 0L,
            "2021-02-06 04:13:56.711720", Collections.singletonList(contributor), Collections.singletonList(1L));

        assertThatThrownBy(() -> {
            toyService.updateById(toyId, updateToyRequestBody);
        }).isInstanceOf(InvalidRequestBodyException.class);
    }

}
