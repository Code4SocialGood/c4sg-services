package org.c4sg.service;

import java.util.List;

import org.c4sg.dto.SkillDTO;
import org.c4sg.dto.SkillUserCountDTO;

public interface SkillService {

    List<SkillDTO> findSkills();
    List<SkillUserCountDTO> findSkillsWithUserCount();
    List<String> findSkillsForUser(Integer id);
    List<String> findSkillsForProject(Integer id);
}
