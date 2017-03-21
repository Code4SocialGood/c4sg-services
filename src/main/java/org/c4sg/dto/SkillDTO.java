package org.c4sg.dto;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

public class SkillDTO {
	@NotNull
	private Integer id;
	
	@NotNull
	@Length(max = 100)
	private String skillName;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getSkillName() {
		return skillName;
	}
	public void setSkillName(String skillName) {
		this.skillName = skillName;
	}
}