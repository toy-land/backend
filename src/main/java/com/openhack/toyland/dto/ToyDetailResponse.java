package com.openhack.toyland.dto;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import com.openhack.toyland.domain.toy.Toy;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class ToyDetailResponse {
    private Long id;
    private String title;
    private String description;
    private String category;
    private String logoUrl;
    private LocalDateTime active;
    private boolean healthCheck;
    private List<String> contributors;
    private String organization;
    private List<String> skills;
    private String period;
    private String githubLink;
    private String serviceLink;

    public ToyDetailResponse(Toy toy) {
        this.id = toy.getId();
        this.title = toy.getTitle();
        this.description = toy.getDescription();
        this.category = toy.getCategory().toString();
        this.logoUrl = toy.getLogoUrl();
        // TODO: 2021/02/04 아래는 하드 코딩
        this.active = LocalDateTime.parse("2021-02-04T16:15:30");
        this.healthCheck = true;
        this.contributors = Arrays.asList("toneyparky", "tongkiparky", "sy9292");
        this.organization = "SOPT";
        this.skills = Arrays.asList("react", "Java", "spring");
        this.period = "LESS_THAN_THREE_DAYS";
        this.githubLink = "https://www.github.com/toneyparky/repository";
        this.serviceLink = "https://www.cocktailpick.com";
    }
}
