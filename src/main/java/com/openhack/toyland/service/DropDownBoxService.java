package com.openhack.toyland.service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import com.openhack.toyland.domain.OrganizationRepository;
import com.openhack.toyland.domain.skill.SkillRepository;
import com.openhack.toyland.domain.toy.Category;
import com.openhack.toyland.domain.toy.Period;
import com.openhack.toyland.dto.OrganizationResponse;
import com.openhack.toyland.dto.SkillResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DropDownBoxService {

	private final SkillRepository skillRepository;
	private final OrganizationRepository organizationRepository;

	public List<SkillResponse> getSkills() {

		return skillRepository.findAll().stream().map(SkillResponse::new).collect(Collectors.toList());
	}

	public List<OrganizationResponse> getOrganizations() {

		return organizationRepository.findAll().stream().map(OrganizationResponse::new).collect(Collectors.toList());
	}

	public List<String> getCategories() {

		return Arrays.stream(Category.values()).map(Enum::name).collect(Collectors.toList());
	}

	public List<String> getPeriods() {

		return Arrays.stream(Period.values()).map(Enum::name).collect(Collectors.toList());
	}
}
