package com.openhack.toyland.domain.skill;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TechStackRepository extends JpaRepository<TechStack, Long> {
    List<TechStack> findAllByToyId(Long toyId);
}
