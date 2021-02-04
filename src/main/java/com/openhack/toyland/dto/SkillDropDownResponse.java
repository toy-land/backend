package com.openhack.toyland.dto;

import java.util.List;
import lombok.Getter;

@Getter
public class SkillDropDownResponse {

	private String message;

	private List<SkillData> data;

	public SkillDropDownResponse(String message, List<SkillData> data) {
		this.message = message;
		this.data = data;
	}
}
