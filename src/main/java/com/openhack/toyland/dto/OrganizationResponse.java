package com.openhack.toyland.dto;

import com.openhack.toyland.domain.Organization;
import lombok.Getter;

@Getter
public class OrganizationResponse {

    private long id;

    private String name;

    public OrganizationResponse(Organization organization) {
        this.id = organization.getId();
        this.name = organization.getName();
    }
}
