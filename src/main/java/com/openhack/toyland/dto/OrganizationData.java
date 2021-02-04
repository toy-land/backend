package com.openhack.toyland.dto;

import com.openhack.toyland.domain.Organization;
import lombok.Getter;

@Getter
public class OrganizationData {

	private long id;

	private String name;

	public OrganizationData(Organization organization) {
		this.id = organization.getId();
		this.name = organization.getName();
	}
}
