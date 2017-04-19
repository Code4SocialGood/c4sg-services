package org.c4sg.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.c4sg.dao.SkillDAO;
import org.c4sg.dto.SkillDTO;
import org.c4sg.dto.SkillUserCountDTO;
import org.c4sg.entity.Skill;
import org.c4sg.mapper.SkillMapper;
import org.c4sg.service.SkillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SkillServiceImpl implements SkillService {

	@Autowired
	private SkillDAO skillDAO;
	
	@Autowired
	private SkillMapper skillMapper;

    public List<SkillDTO> findSkills() {
    	List<Skill> skills = skillDAO.findAllByOrderBySkillNameAsc();
    	List<SkillDTO> skillDTOS = skills.stream().map(o -> skillMapper.getSkillDtoFromEntity(o)).collect(Collectors.toList());
        return skillDTOS;
    }
    public List<SkillUserCountDTO> findSkillsWithUserCount() {
    	List<SkillUserCountDTO> skillUserCountDTOs = new ArrayList<SkillUserCountDTO>();
    	List<Map<String, Object>> skillUserCountList = skillDAO.findSkillsAndUserCount();
    	for(Map<String, Object> skillMap: skillUserCountList)	{
    		skillUserCountDTOs.add(skillMapper.getSkillUserCountDto(skillMap));
    	}
		return skillUserCountDTOs;
    }
	public List<String> findSkillsForUser(Integer id) {
		List<Map<String, Object>> skillsForUser = skillDAO.findSkillsByUserId(id);
		List<String> userSkills = new ArrayList<String>();
		for(Map<String, Object> skills: skillsForUser)	{
    		userSkills.add((String)skills.get("skillName"));
    	}
		return userSkills;
	}
	@Override
	public List<String> findSkillsForProject(Integer id) {
		List<Map<String, Object>> skillsForProject = skillDAO.findSkillsByProjectId(id);
		List<String> projectSkills = new ArrayList<String>();
		for(Map<String, Object> skills: skillsForProject)	{
    		projectSkills.add((String)skills.get("skillName"));
    	}
		return projectSkills;
	}
}
