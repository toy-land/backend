package com.openhack.toyland.dto;

import java.util.List;
import lombok.Getter;

@Getter
public class EnumDropDownResponse {
	private String message;

	private List<String> data;

	public EnumDropDownResponse(String message, List<String> data) {
		this.message = message;
		this.data = data;
	}
}
