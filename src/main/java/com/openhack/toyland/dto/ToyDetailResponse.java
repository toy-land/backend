package com.openhack.toyland.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.openhack.toyland.domain.Organization;
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
    private String readme;
    private String category;
    private String logoUrl;
    private LocalDateTime active;
    private boolean healthCheck;
    private List<UserResponse> contributors;
    private String organization;
    private List<String> skills;
    private String period;
    private String githubLink;
    private String serviceLink;

    public ToyDetailResponse(Toy toy, Organization organization, List<String> skillNames, List<UserResponse> users) {
        this.id = toy.getId();
        this.title = toy.getTitle();
        this.description = toy.getDescription();
        this.readme = toy.getReadme();
        this.category = toy.getCategory().toString();
        this.logoUrl = toy.getLogoUrl();
        // TODO: 2021/02/04 아래 두개는 하드 코딩
        this.active = LocalDateTime.parse("2021-02-04T16:15:30");
        this.healthCheck = true;
        this.contributors = users;
        this.organization = organization.getName();
        this.skills = skillNames;
        this.period = toy.getPeriod().toString();
        this.githubLink = toy.getGithubLink();
        this.serviceLink = toy.getServiceLink();
    }
}
