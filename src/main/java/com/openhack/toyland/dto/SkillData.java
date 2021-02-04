package com.openhack.toyland.dto;

import com.openhack.toyland.domain.skill.Skill;
import lombok.Getter;

@Getter
public class SkillData {

	private long id;

	private String name;

	public SkillData(Skill skill) {
		this.id = skill.getId();
		this.name = skill.getName();
	}
}
