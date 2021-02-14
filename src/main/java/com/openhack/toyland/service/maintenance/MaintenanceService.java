package com.openhack.toyland.service.maintenance;

import java.time.LocalDateTime;
import java.util.List;

import com.openhack.toyland.domain.UpdatableMaintenance;
import org.springframework.stereotype.Service;

import com.openhack.toyland.domain.Maintenance;
import com.openhack.toyland.domain.MaintenanceRepository;
import com.openhack.toyland.exception.DuplicatedEntityException;
import com.openhack.toyland.infra.ApiParser;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class MaintenanceService {
    private final MaintenanceRepository repository;
    private final ApiParser apiParser;

    public List<Maintenance> findAll() {
        return repository.findAll();
    }

    public List<UpdatableMaintenance> findAllNeedsHealthCheck() {
        return repository.findAllByNeedsHealthCheck();
    }

    public void associate(LocalDateTime pushedAt, String serviceLink, Long toyId) {
        if (repository.existsByToyId(toyId)) {
            throw new DuplicatedEntityException("해당 toy에 대한 maintenance가 이미 저장되어 있습니다.");
        }

        boolean isHealthy = apiParser.checkHealth(serviceLink);

        Maintenance maintenance = Maintenance.builder()
            .toyId(toyId)
            .active(pushedAt)
            .healthCheck(isHealthy)
            .sleepDays(isHealthy ? 0L : 1L)
            .build();

        repository.save(maintenance);
    }

    public void updateAll(List<Maintenance> maintenances) {
        repository.saveAll(maintenances);
    }

    public List<Maintenance> findBySleepDaysGreaterThan(long threshold) {
        return repository.findBySleepDaysGreaterThan(threshold);
    }
}
