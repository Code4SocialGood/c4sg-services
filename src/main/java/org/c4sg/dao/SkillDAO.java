package org.c4sg.dao;

import java.util.List;

import org.c4sg.entity.Skill;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface SkillDAO extends CrudRepository<Skill, Integer> {
	
    /*
    String FIND_SKILL_USERCOUNT ="select s.skillName as skillName, count(*) as userCount from UserSkill us "
				+"inner join us.skill s group by us.skill "				
				+"order by userCount desc, skillName"; */
    
    String FIND_SKILLS_BY_COUNT = 
    		"select skill_id, skill_name, sum(skillCount) as count " + 
    		"from ( " + 
    		"select s1.id as skill_id, s1.skill_name as skill_name, count(*) as skillCount " +  
    		"from user_skill us " + 
    		"join skill s1  on us.skill_id = s1.id " + 
    		"group by us.skill_id " + 
    		"union " + 
    		"select s2.id as skill_id, s2.skill_name as skill_name, count(*) as skillCount " + 
    		"from project_skill ps " + 
    		"join skill s2 on ps.skill_id = s2.id " + 
    		"group by ps.skill_id) as temp " + 
    		"group by temp.skill_name " + 
    		"order by count desc";
    
	Skill findBySkillName(String skillName);
	List<Skill> findAllByOrderBySkillNameAsc();
	
    @Query(value = FIND_SKILLS_BY_COUNT, nativeQuery = true)
    List<Object[]> findSkillsbyCount();
}
