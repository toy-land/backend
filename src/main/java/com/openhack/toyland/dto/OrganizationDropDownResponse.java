package com.openhack.toyland.dto;

import java.util.List;
import lombok.Getter;

@Getter
public class OrganizationDropDownResponse {
	private String message;

	private List<OrganizationResponse> reposponse;

	public OrganizationDropDownResponse(String message, List<OrganizationResponse> reposponse) {
		this.message = message;
		this.reposponse = reposponse;
	}
}
