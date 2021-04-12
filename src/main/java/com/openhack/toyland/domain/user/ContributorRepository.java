package com.openhack.toyland.domain.user;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContributorRepository extends JpaRepository<Contributor, Long> {
    void deleteAllByToyId(Long toyId);

    List<Contributor> findAllByToyId(Long toyId);
}
