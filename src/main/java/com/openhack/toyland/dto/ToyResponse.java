package com.openhack.toyland.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.openhack.toyland.domain.Maintenance;
import com.openhack.toyland.domain.skill.Skill;
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
    private List<Skill> skills;

    public ToyResponse(Toy toy, Maintenance maintenance, List<Skill> skills) {
        this.id = toy.getId();
        this.title = toy.getTitle();
        this.description = toy.getDescription();
        this.logoUrl = toy.getLogoUrl();
        this.category = toy.getCategory().toString();
        this.active = maintenance.getActive();
        this.skills = skills;
    }
}
