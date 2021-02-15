package com.openhack.toyland.service.maintenance;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import com.openhack.toyland.domain.Maintenance;
import com.openhack.toyland.domain.UpdatableMaintenance;
import com.openhack.toyland.domain.toy.Toy;
import com.openhack.toyland.dto.EmailParticipant;
import com.openhack.toyland.infra.ApiParser;
import com.openhack.toyland.infra.MailServer;
import com.openhack.toyland.service.toy.ToyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j(topic = "[Schedule]:")
@RequiredArgsConstructor
@Service
public class ScheduleService {
    private final MaintenanceService maintenanceService;
    private final ToyService toyService;
    private final ApiParser apiParser;
    private final MailServer mailServer;

    private final long THRESHOLD = 6;

    @Scheduled(cron = "0 0 0 * * *")
    @Transactional
    public void updateMaintenance() {
        List<UpdatableMaintenance> maintenances = maintenanceService.findAllNeedsHealthCheck();
        List<Maintenance> updates = new ArrayList<>();
        for (UpdatableMaintenance updatableMaintenance : maintenances) {
            log.info("maintenance: " + updatableMaintenance.getMaintenance().toString());
            log.info("service-link: " + updatableMaintenance.getServiceLink());
            log.info("github-link: " + updatableMaintenance.getGithubLink());
            boolean isHealthy = apiParser.checkHealth(updatableMaintenance.getServiceLink());
            LocalDateTime active = apiParser
                .fetchActive(updatableMaintenance.getGithubLink(), updatableMaintenance.getMaintenance().getActive());
            log.warn(active.toString());
            Maintenance updated = Maintenance.builder()
                .id(updatableMaintenance.getMaintenance().getId())
                .toyId(updatableMaintenance.getMaintenance().getToyId())
                .active(active)
                .healthCheck(isHealthy)
                .sleepDays(isHealthy ? 0 : updatableMaintenance.getMaintenance().getSleepDays() + 1L)
                .build();
            updates.add(updated);
        }
        maintenanceService.updateAll(updates);
    }

    @Scheduled(cron = "0 5 0 * * *")
    @Transactional
    public void sendMail() throws NoSuchAlgorithmException, InvalidKeyException, UnsupportedEncodingException {
        List<Maintenance> maintenances = maintenanceService.findBySleepDaysGreaterThan(THRESHOLD);
        // TODO: email 링크가 비어 있거나 null일 경우 안 가져오도록
        List<EmailParticipant> emailParticipants = new ArrayList<>();
        for (Maintenance maintenance : maintenances) {
            Toy toy = toyService.findEntityByIdAndEmailIsNotNull(maintenance.getToyId());
            EmailParticipant emailParticipant =
                new EmailParticipant(toy.getEmail(), toy.getTitle(), maintenance.getSleepDays());
            emailParticipants.add(emailParticipant);
        }
        log.info(emailParticipants.toString());
        mailServer.sendMail(emailParticipants);
    }
}
