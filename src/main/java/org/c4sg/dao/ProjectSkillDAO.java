package org.c4sg.dao;

import java.util.List;
import java.util.Map;

import org.c4sg.entity.ProjectSkill;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface ProjectSkillDAO extends CrudRepository<ProjectSkill, Long> {
    ProjectSkill findByProject_IdAndSkill_Id(Integer projectId, Integer skillId);
    List<ProjectSkill> findByProjectId(Integer ProjectId);
    int countByProjectIdAndSkillId(Integer id, Integer skillId);
    int countByProjectId(Integer id);
	
    @Transactional
    void deleteByProjectId(Integer id);
    
    String FIND_SKILL_FOR_PROJECT ="select s.skillName as skillName "
				  +"from ProjectSkill ps inner join ps.skill s where ps.project.id= :id order by ps.displayOrder";

    @Query(FIND_SKILL_FOR_PROJECT)
    List<Map<String, Object>> findSkillsByProjectId(@Param("id") Integer id);	
}
