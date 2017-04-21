package org.c4sg.service;

import java.util.List;

import org.c4sg.dto.SkillDTO;
import org.c4sg.dto.SkillUserCountDTO;
import org.c4sg.entity.Skill;
import org.c4sg.exception.SkillException;

public interface SkillService {
    List<SkillDTO> findSkills();
    List<SkillUserCountDTO> findSkillsWithUserCount();
    List<String> findSkillsForUser(Integer id);
    List<String> findSkillsForProject(Integer id);
    void saveSkillsForUser(Integer id, List<String> skillsList) throws SkillException;
    void saveSkillsForProject(Integer id, List<String> skillsList) throws SkillException;
}
