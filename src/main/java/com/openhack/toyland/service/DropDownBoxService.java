package com.openhack.toyland.service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import com.openhack.toyland.domain.OrganizationRepository;
import com.openhack.toyland.domain.skill.SkillRepository;
import com.openhack.toyland.domain.toy.Category;
import com.openhack.toyland.domain.toy.Period;
import com.openhack.toyland.dto.OrganizationData;
import com.openhack.toyland.dto.SkillData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DropDownBoxService {

	private SkillRepository skillRepository;
	private OrganizationRepository organizationRepository;

	@Autowired
	DropDownBoxService(SkillRepository skillRepository, OrganizationRepository organizationRepository) {
		this.skillRepository = skillRepository;
		this.organizationRepository = organizationRepository;
	}

	public List<SkillData> getSkills() {

		return skillRepository.findAll().stream().map(SkillData::new).collect(Collectors.toList());
	}

	public List<OrganizationData> getOrganizations() {

		return organizationRepository.findAll().stream().map(OrganizationData::new).collect(Collectors.toList());
	}

	public List<String> getCategories() {

		return Arrays.stream(Category.values()).map(Enum::name).collect(Collectors.toList());
	}

	public List<String> getPeriods() {

		return Arrays.stream(Period.values()).map(Enum::name).collect(Collectors.toList());
	}
}
