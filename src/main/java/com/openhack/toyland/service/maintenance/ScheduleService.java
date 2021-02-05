package com.openhack.toyland.service.maintenance;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.openhack.toyland.domain.Maintenance;
import com.openhack.toyland.dto.ToyDetailResponse;
import com.openhack.toyland.infra.ApiParser;
import com.openhack.toyland.service.toy.ToyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class ScheduleService {
    private final MaintenanceService maintenanceService;
    private final ToyService toyService;
    private final ApiParser apiParser;

    @Scheduled(cron = "0 0 0 * * *")
    @Transactional
    public void updateMaintenance() {
        List<Maintenance> maintenances = maintenanceService.findAll();
        List<Maintenance> updates = new ArrayList<>();
        for (Maintenance maintenance : maintenances) {
            ToyDetailResponse toy = toyService.findById(maintenance.getToyId());
            boolean isHealthy = apiParser.checkHealth(toy.getServiceLink());
            LocalDateTime active = apiParser.fetchActive(toy.getGithubLink(), toy.getActive());
            log.warn(active.toString());
            Maintenance updated = Maintenance.builder()
                .id(maintenance.getId())
                .toyId(maintenance.getToyId())
                .active(active)
                .healthCheck(isHealthy)
                .sleepDays(isHealthy ? 0 : maintenance.getSleepDays() + 1L)
                .build();
            updates.add(updated);
        }
        maintenanceService.updateAll(updates);
    }
}
