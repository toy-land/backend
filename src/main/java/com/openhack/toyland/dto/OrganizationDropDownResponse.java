package com.openhack.toyland.dto;

import java.util.List;
import lombok.Getter;

@Getter
public class OrganizationDropDownResponse {
	private String message;

	private List<OrganizationData> data;

	public OrganizationDropDownResponse(String message, List<OrganizationData> data) {
		this.message = message;
		this.data = data;
	}
}
