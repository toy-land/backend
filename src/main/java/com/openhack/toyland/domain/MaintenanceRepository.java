package com.openhack.toyland.domain;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MaintenanceRepository extends JpaRepository<Maintenance, Long> {
    boolean existsByToyId(Long toyId);
}
