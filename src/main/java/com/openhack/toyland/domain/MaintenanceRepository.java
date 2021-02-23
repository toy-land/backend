package com.openhack.toyland.domain;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface MaintenanceRepository extends JpaRepository<Maintenance, Long> {
    boolean existsByToyId(Long toyId);

    Optional<Maintenance> findByToyId(Long toyId);

    @Transactional
    void deleteByToyId(Long deleteByToyId);

    List<Maintenance> findBySleepDaysGreaterThan(Long threshold);

    @Query("select m as maintenance, t.githubLink as githubLink, t.serviceLink as serviceLink from Maintenance m inner join Toy t on t.id=m.toyId where t.serviceLink is not null and t.serviceLink <> '' and t.email is not null and t.email <> ''")
    List<UpdatableMaintenance> findAllByNeedsHealthCheck();
}
