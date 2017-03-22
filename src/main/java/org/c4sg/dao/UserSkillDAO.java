package org.c4sg.dao;

import java.util.List;

import org.c4sg.entity.UserSkill;
import org.springframework.data.repository.CrudRepository;

public interface UserSkillDAO extends CrudRepository<UserSkill, Long>{
    UserSkill findByUser_IdAndSkill_Id(Integer userId, Integer skillId);
    List<UserSkill> findByUserId(Integer userId);
}
