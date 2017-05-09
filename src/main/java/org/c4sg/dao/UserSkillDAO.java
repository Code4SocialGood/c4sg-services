package org.c4sg.dao;

import java.util.List;
import java.util.Map;

import org.c4sg.entity.UserSkill;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface UserSkillDAO extends CrudRepository<UserSkill, Long>{
    UserSkill findByUser_IdAndSkill_Id(Integer userId, Integer skillId);
    int countByUserId(Integer id);
    int countByUserIdAndSkillId(Integer id,Integer skillId);
    
    @Transactional
    void deleteByUserId(Integer id);
    
    List<UserSkill> findByUserId(Integer userId);
    String FIND_SKILL_USERCOUNT ="select s.skillName as skillName, count(*) as userCount from UserSkill us "
				+"inner join us.skill s group by us.skill "
				+"order by userCount desc, skillName";

    String FIND_SKILL_FOR_USER  ="select s.skillName as skillName "
				+"from UserSkill us inner join us.skill s where us.user.id= :id order by us.displayOrder";
    
    // Native query for temporal table
    @Query(value = "SELECT s.* FROM skill s JOIN (select skill_id, count(skill_id) as userCount from user_skill group by skill_id ) us ON ( us.skill_id = s.id ) ORDER BY us.userCount, s.skill_name DESC", nativeQuery = true)
    List<Object[]> findSkillsbyCount();

    @Query(FIND_SKILL_FOR_USER)
    List<Map<String, Object>> findSkillsByUserId(@Param("id") Integer id);
}