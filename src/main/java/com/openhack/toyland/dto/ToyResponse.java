package com.openhack.toyland.dto;

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

    public ToyResponse(Toy toy) {
        this.id = toy.getId();
        this.title = toy.getTitle();
        this.description = toy.getDescription();
        this.logoUrl = toy.getLogoUrl();
        this.category = toy.getCategory().toString();
    }
}
