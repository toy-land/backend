package com.openhack.toyland.dto;

import java.util.List;

import lombok.Getter;

@Getter
public class SkillDropDownResponse {

    private List<SkillResponse> data;

    public SkillDropDownResponse(List<SkillResponse> data) {
        this.data = data;
    }
}
