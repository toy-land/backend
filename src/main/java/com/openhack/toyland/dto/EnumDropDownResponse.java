package com.openhack.toyland.dto;

import java.util.List;

import lombok.Getter;

@Getter
public class EnumDropDownResponse {
    private List<String> reposponse;

    public EnumDropDownResponse(List<String> reposponse) {
        this.reposponse = reposponse;
    }
}
