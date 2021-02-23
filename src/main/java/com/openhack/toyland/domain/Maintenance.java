package com.openhack.toyland.domain;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@ToString
public class Maintenance extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "toy_id", unique = true)
    private Long toyId;

    private Boolean healthCheck;

    private Long sleepDays = 0L;

    private LocalDateTime active;

    @Builder
    public Maintenance(Long id, Long toyId, Boolean healthCheck, Long sleepDays, LocalDateTime active) {
        this.id = id;
        this.toyId = toyId;
        this.healthCheck = healthCheck;
        this.sleepDays = sleepDays;
        this.active = active;
    }
}
