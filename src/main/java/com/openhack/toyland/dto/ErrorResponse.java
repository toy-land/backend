package com.openhack.toyland.dto;

import lombok.Getter;

@Getter
public class ErrorResponse {

	private String message;

	public ErrorResponse(String message) {
		this.message = message;
	}
}
