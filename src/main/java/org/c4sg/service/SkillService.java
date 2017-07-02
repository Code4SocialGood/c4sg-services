package org.c4sg.service;

import java.util.List;
import org.c4sg.dto.SkillDTO;

public interface SkillService {
    List<SkillDTO> findSkills();
    List<SkillDTO> findSkillsbyCount();
    List<String> findSkillsForUser(Integer id);
    List<String> findSkillsForProject(Integer id);
    void saveSkillsForUser(Integer id, List<String> skillsList);
    void saveSkillsForProject(Integer id, List<String> skillsList);
}
