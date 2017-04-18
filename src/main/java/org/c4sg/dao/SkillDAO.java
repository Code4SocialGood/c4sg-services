package org.c4sg.dao;

import java.util.List;
import java.util.Map;

import org.c4sg.entity.Skill;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface SkillDAO extends CrudRepository<Skill, Integer> {
	
	List<Skill> findAllByOrderBySkillNameAsc();
	
	String FIND_SKILL_USERCOUNT ="select s.skillName as skillName, count(*) as userCount from UserSkill us "
					+"inner join us.skill s group by us.skill "
								+"order by userCount desc, skillName";
	
	String FIND_SKILL_FOR_USER  ="select s.skillName as skillName "
					+"from UserSkill us inner join us.skill s where us.user.id= :id order by us.displayOrder";
	
	String FIND_SKILL_FOR_PROJECT ="select s.skillName as skillName "
					+"from ProjectSkill ps inner join ps.skill s where ps.project.id= :id order by ps.displayOrder";

    	@Query(FIND_SKILL_USERCOUNT)
    	List<Map<String, Object>> findSkillsAndUserCount();
    
	@Query(FIND_SKILL_FOR_USER)
	List<Map<String, Object>> findSkillsByUserId(@Param("id") Integer id);
		
	@Query(FIND_SKILL_FOR_PROJECT)
	List<Map<String, Object>> findSkillsByProjectId(@Param("id") Integer id);
}
