package com.openhack.toyland.service.toy;

import static org.assertj.core.api.AssertionsForClassTypes.*;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.openhack.toyland.IntegrationTest;
import com.openhack.toyland.domain.toy.Toy;
import com.openhack.toyland.domain.toy.ToyRepository;
import com.openhack.toyland.dto.ToyDetailResponse;
import com.openhack.toyland.exception.EntityNotFoundException;
import com.openhack.toyland.exception.UnAuthorizedEventException;

public class ToyDeleteServiceTest extends IntegrationTest {

    private final long toyId = 5L;
    private final String password = "toytoytoy";

    @Autowired
    private ToyRepository toyRepository;

    @Autowired
    private ToyService toyService;

    @DisplayName("toy deleteById 성공 테스트")
    @Test
    void deleteByIdSuccess() {
        toyService.deleteById(toyId, password);
        Optional<Toy> deletedToy = toyRepository.findById(toyId);

        Assertions.assertFalse(deletedToy.isPresent());
    }

    @DisplayName("toy deleteById 실패 테스트 - toy id 없음 오류")
    @Test
    void deleteByIdFailedByToyIdOutOfBoundary() {

        assertThatThrownBy(() -> {
            toyService.deleteById(100L, password);
        }).isInstanceOf(EntityNotFoundException.class);
    }

    @DisplayName("toy deleteById 실패 테스트 - 비밀번호 오류")
    @Test
    void deleteByIdFailedByDifferentPassword() {

        assertThatThrownBy(() -> {
            toyService.deleteById(toyId, "password");
        }).isInstanceOf(UnAuthorizedEventException.class);
    }
}
