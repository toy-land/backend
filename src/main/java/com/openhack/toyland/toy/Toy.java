package com.openhack.toyland.toy;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.validation.constraints.NotNull;

import com.openhack.toyland.BaseTimeEntity;
import lombok.AccessLevel;
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
    private String logoUrl;

    @Lob
    private String githubLink;

    @Lob
    private String serviceLink;

    private String email;

    private Integer organizationId;

    @Enumerated(EnumType.STRING)
    private Category category;

    @Enumerated(EnumType.STRING)
    private Period period;
}
