package com.openhack.toyland.dto;

import com.openhack.toyland.domain.toy.Toy;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class SimpleToyResponse {
    private Long id;
    private String name;

    public SimpleToyResponse(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public static SimpleToyResponse from(Toy toy) {
        return new SimpleToyResponse(toy.getId(), toy.getTitle());
    }
}
