package com.openhack.toyland.dto;

import java.util.List;
import lombok.Getter;

@Getter
public class EnumDropDownResponse {
	private String message;

	private List<String> reposponse;

	public EnumDropDownResponse(String message, List<String> reposponse) {
		this.message = message;
		this.reposponse = reposponse;
	}
}
