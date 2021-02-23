package com.openhack.toyland.service.toy;

import static org.assertj.core.api.AssertionsForClassTypes.*;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.openhack.toyland.IntegrationTest;
import com.openhack.toyland.domain.MaintenanceRepository;
import com.openhack.toyland.domain.Organization;
import com.openhack.toyland.domain.OrganizationRepository;
import com.openhack.toyland.dto.ToyDetailResponse;
import com.openhack.toyland.exception.EntityNotFoundException;

public class ToyFindSingleServiceTest extends IntegrationTest {

    private final long toyId = 5L;

    @Autowired
    private OrganizationRepository organizationRepository;

    @Autowired
    private MaintenanceRepository maintenanceRepository;

    @Autowired
    private ToyService toyService;

    @DisplayName("toy findById 성공 테스트")
    @Test
    void findByIdSuccess() {
        ToyDetailResponse toyDetailResponse = toyService.findById(toyId);

        Assertions.assertEquals(toyId, toyDetailResponse.getId());
    }

    @DisplayName("toy findById 실패 테스트 - toy id 없음 오류")
    @Test
    void findByIdFailedByToyIdOutOfBoundary() {

        assertThatThrownBy(() -> {
            toyService.findById(100L);
        }).isInstanceOf(EntityNotFoundException.class);
    }

    @DisplayName("toy findById 실패 테스트 - organization 없음 오류")
    @Test
    void findByIdFailedByOrganizationNotFound() {
        ToyDetailResponse toyDetailResponse = toyService.findById(toyId);
        Organization organizations = organizationRepository.findAll().stream()
            .filter(organization -> StringUtils.equals(organization.getName(), toyDetailResponse.getOrganization()))
            .findFirst().get();
        organizationRepository.delete(organizations);

        assertThatThrownBy(() -> {
            toyService.findById(toyId);
        }).isInstanceOf(EntityNotFoundException.class);
    }

    @DisplayName("toy findById 실패 테스트 - maintenance 없음 오류")
    @Test
    @Transactional
    void findByIdFailedByMaintenanceNotFound() {
        maintenanceRepository.deleteByToyId(toyId);

        assertThatThrownBy(() -> {
            toyService.findById(toyId);
        }).isInstanceOf(EntityNotFoundException.class);
    }
}
