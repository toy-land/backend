package com.openhack.toyland.dto;

import java.time.LocalDateTime;

import com.openhack.toyland.domain.toy.Toy;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class ToyResponse {
    private Long id;
    private String title;
    private String description;
    private String logoUrl;
    private String category;
    private LocalDateTime active;

    public ToyResponse(Toy toy) {
        this.id = toy.getId();
        this.title = toy.getTitle();
        this.description = toy.getDescription();
        this.logoUrl = toy.getLogoUrl();
        this.category = toy.getCategory().toString();
        // TODO: 2021/02/05 maintenance구현 후 active 수정
        this.active = LocalDateTime.parse("2021-02-04T16:15:30");
    }
}
