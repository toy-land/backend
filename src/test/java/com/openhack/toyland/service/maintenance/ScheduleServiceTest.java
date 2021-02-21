package com.openhack.toyland.service.maintenance;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.openhack.toyland.PersistenceTest;
import com.openhack.toyland.domain.UpdatableMaintenance;

@SpringBootTest
public class ScheduleServiceTest extends PersistenceTest {

    @Autowired
    ScheduleService scheduleService;

    @Autowired
    MaintenanceService maintenanceService;

    @DisplayName("toy.service_link 가 빈 문자열이 아닌 maintenance 를 가져오는 테스트")
    @Test
    public void testNotToFindToyServiceLinkIsEmpty() {
        List<UpdatableMaintenance> updatableMaintenanceList = maintenanceService.findAllNeedsHealthCheck();
        for (UpdatableMaintenance updatableMaintenance : updatableMaintenanceList) {
            Assertions.assertNotNull(updatableMaintenance.getServiceLink());
        }
    }

    @Test
    public void testUpdateHealthCheck() {
        scheduleService.updateMaintenance();
    }
}
