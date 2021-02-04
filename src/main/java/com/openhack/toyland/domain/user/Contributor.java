package com.openhack.toyland.domain.user;

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
public class Contributor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private Long toyId;

    @NotNull
    private Long userId;

    public Contributor(@NotNull Long toyId, @NotNull Long userId) {
        this.id = null;
        this.toyId = toyId;
        this.userId = userId;
    }
}
