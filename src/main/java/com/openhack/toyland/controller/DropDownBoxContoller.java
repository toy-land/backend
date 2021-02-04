package com.openhack.toyland.controller;

import java.util.List;
import com.openhack.toyland.dto.EnumDropDownResponse;
import com.openhack.toyland.dto.OrganizationResponse;
import com.openhack.toyland.dto.OrganizationDropDownResponse;
import com.openhack.toyland.dto.SkillResponse;
import com.openhack.toyland.dto.SkillDropDownResponse;
import com.openhack.toyland.service.DropDownBoxService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@CrossOrigin("*")
@Controller
public class DropDownBoxContoller {

	@Autowired
	DropDownBoxService dropDownBoxService;

	DropDownBoxContoller(DropDownBoxService dropDownBoxService) {
		this.dropDownBoxService = dropDownBoxService;
	}

	@GetMapping("/api/skills")
	public ResponseEntity<?> getSkill() {
		List<SkillResponse> skills = dropDownBoxService.getSkills();
		log.info(skills.toString());
		SkillDropDownResponse dropDownResponse = new SkillDropDownResponse("skill 목록 조회 성공", skills);
		return ResponseEntity.ok().body(dropDownResponse);
	}

	@GetMapping("/api/organizations")
	public ResponseEntity<?> getOrganization() {
		List<OrganizationResponse> organizations = dropDownBoxService.getOrganizations();
		log.info(organizations.toString());
		OrganizationDropDownResponse dropDownResponse =
			new OrganizationDropDownResponse("organization 목록 조회 성공", organizations);
		return ResponseEntity.ok().body(dropDownResponse);
	}

	@GetMapping("/api/categories")
	public ResponseEntity<?> getCategory() {
		List<String> categories = dropDownBoxService.getCategories();
		log.info(categories.toString());
		EnumDropDownResponse dropDownResponse = new EnumDropDownResponse("category 목록 조회 성공", categories);
		return ResponseEntity.ok().body(dropDownResponse);
	}

	@GetMapping("/api/periods")
	public ResponseEntity<?> getPeriod() {
		List<String> periods = dropDownBoxService.getPeriods();
		log.info(periods.toString());
		EnumDropDownResponse dropDownResponse = new EnumDropDownResponse("period 목록 조회 성공", periods);
		return ResponseEntity.ok().body(dropDownResponse);
	}
}
