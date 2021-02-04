package com.openhack.toyland.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, Long> {
    @Modifying
    @Query("UPDATE User u SET u.username = :username WHERE u.githubIdentifier = :githubIdentifier AND u.username <> :username")
    void updateUsername(@Param("username") String username, @Param("githubIdentifier") Long githubIdentifier);
}
