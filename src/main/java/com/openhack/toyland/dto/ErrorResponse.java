package com.openhack.toyland.dto;

import org.springframework.http.HttpStatus;

public class ErrorResponse {

	private String message;

	public ErrorResponse(String message) {
		this.message = message;
	}
}
