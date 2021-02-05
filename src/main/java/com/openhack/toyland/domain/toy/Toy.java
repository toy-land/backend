package com.openhack.toyland.domain.toy;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.validation.constraints.NotNull;

import com.openhack.toyland.domain.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Toy extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private Long githubIdentifier;

    @NotNull
    private String title;

    @NotNull
    private String password;

    @NotNull
    @Lob
    private String description;

    @Lob
    private String readme;

    @Lob
    private String logoUrl;

    @NotNull
    @Lob
    private String githubLink;

    @Lob
    private String serviceLink;

    private String email;

    private Long organizationId;

    @Enumerated(EnumType.STRING)
    private com.openhack.toyland.domain.toy.Category category;

    @Enumerated(EnumType.STRING)
    private com.openhack.toyland.domain.toy.Period period;

    @Builder
    public Toy(Long githubIdentifier, String title, String password, String description, String readme, String logoUrl,
        String githubLink, String serviceLink, String email, Long organizationId, String category, String period, Long id) {
        this.id = id;
        this.githubIdentifier = githubIdentifier;
        this.title = title;
        this.password = password;
        this.description = description;
        this.readme = readme;
        this.logoUrl = logoUrl;
        this.githubLink = githubLink;
        this.serviceLink = serviceLink;
        this.email = email;
        this.organizationId = organizationId;
        this.category = Category.valueOf(category);
        this.period = Period.valueOf(period);
    }
}
