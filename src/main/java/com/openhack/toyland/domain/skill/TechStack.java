package com.openhack.toyland.domain.skill;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class TechStack {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private Long toyId;

    @NotNull
    private Long skillId;

    public TechStack(Long toyId, Long skillId) {
        this.id = null;
        this.toyId = toyId;
        this.skillId = skillId;
    }
}
