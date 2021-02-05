package com.openhack.toyland.service;

import java.net.UnknownHostException;
import java.time.LocalDateTime;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.openhack.toyland.domain.Maintenance;
import com.openhack.toyland.domain.MaintenanceRepository;
import com.openhack.toyland.exception.DuplicatedEntityException;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class MaintenanceService {
    private final MaintenanceRepository repository;
    private final RestTemplate restTemplate;

    public void associate(LocalDateTime pushedAt, String serviceLink, Long toyId) {
        if (repository.existsByToyId(toyId)) {
            throw new DuplicatedEntityException("해당 toy에 대한 maintenance가 이미 저장되어 있습니다.");
        }

        boolean isHealthy = check(serviceLink);

        Maintenance maintenance = Maintenance.builder()
            .toyId(toyId)
            .active(pushedAt)
            .healthCheck(isHealthy)
            .build();

        repository.save(maintenance);
    }

    private boolean check(String link) {
        ResponseEntity<String> response = null;
        try {
            response =  restTemplate.getForEntity(link, String.class);
        } catch (Exception e) {
            return false;
        }
        return !response.getStatusCode().isError();
    }
}
