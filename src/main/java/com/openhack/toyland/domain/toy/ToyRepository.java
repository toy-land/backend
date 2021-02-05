package com.openhack.toyland.domain.toy;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ToyRepository extends JpaRepository<Toy, Long> {
    boolean existsByGithubIdentifier(Long githubIdentifier);

    Optional<Toy> findByIdAndEmailIsNotNull(Long id);
}
