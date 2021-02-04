package com.openhack.toyland.dto;

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
}
