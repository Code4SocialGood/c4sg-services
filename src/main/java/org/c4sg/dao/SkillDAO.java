package org.c4sg.dao;

import java.util.List;

import org.c4sg.entity.Skill;
import org.springframework.data.repository.CrudRepository;

public interface SkillDAO extends CrudRepository<Skill, Integer> {
	Skill findBySkillName(String skillName);
	List<Skill> findAllByOrderBySkillNameAsc();
}
