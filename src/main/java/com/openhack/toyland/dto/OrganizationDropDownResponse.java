package com.openhack.toyland.dto;

import java.util.List;

import lombok.Getter;

@Getter
public class OrganizationDropDownResponse {
    // TODO: typo response
    private List<OrganizationResponse> reposponse;

    public OrganizationDropDownResponse(List<OrganizationResponse> reposponse) {
        this.reposponse = reposponse;
    }
}
