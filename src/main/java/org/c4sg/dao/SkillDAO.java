package org.c4sg.dao;

import java.util.List;
import java.util.Map;

import org.c4sg.entity.Skill;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface SkillDAO extends CrudRepository<Skill, Integer> {
	Skill findBySkillName(String skillName);
	List<Skill> findAllByOrderBySkillNameAsc();
}
