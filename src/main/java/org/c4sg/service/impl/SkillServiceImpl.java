package org.c4sg.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.Objects.requireNonNull;
import static java.util.Objects.isNull;

import org.c4sg.dao.ProjectDAO;
import org.c4sg.dao.ProjectSkillDAO;
import org.c4sg.dao.SkillDAO;
import org.c4sg.dao.UserDAO;
import org.c4sg.dao.UserSkillDAO;
import org.c4sg.dto.SkillDTO;
import org.c4sg.dto.SkillUserCountDTO;
import org.c4sg.entity.ProjectSkill;
import org.c4sg.entity.Skill;
import org.c4sg.entity.UserSkill;
import org.c4sg.exception.SkillException;
import org.c4sg.mapper.SkillMapper;
import org.c4sg.service.SkillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SkillServiceImpl implements SkillService {

	@Autowired
	private SkillDAO skillDAO;
	@Autowired
	private UserSkillDAO userSkillDAO;
	@Autowired
	private ProjectSkillDAO projectSkillDAO;
	@Autowired
	private SkillMapper skillMapper;
	@Autowired
	private UserDAO userDAO;
	@Autowired
	private ProjectDAO projectDAO;
	
	@Override
    public List<SkillDTO> findSkills() {
    	List<Skill> skills = skillDAO.findAllByOrderBySkillNameAsc();
    	List<SkillDTO> skillDTOS = skills.stream().map(o -> skillMapper.getSkillDtoFromEntity(o)).collect(Collectors.toList());
        return skillDTOS;
    }
    @Override
    public List<SkillUserCountDTO> findSkillsWithUserCount() {
    	List<SkillUserCountDTO> skillUserCountDTOs = new ArrayList<SkillUserCountDTO>();
    	List<Map<String, Object>> skillUserCountList = userSkillDAO.findSkillsAndUserCount();
    	for(Map<String, Object> skillMap: skillUserCountList)	{
    		skillUserCountDTOs.add(skillMapper.getSkillUserCountDto(skillMap));
    	}
		return skillUserCountDTOs;
    }
    @Override
	public List<String> findSkillsForUser(Integer id) {
		List<Map<String, Object>> skillsForUser = userSkillDAO.findSkillsByUserId(id);
		List<String> userSkills = new ArrayList<String>();
		for(Map<String, Object> skills: skillsForUser)	{
    		userSkills.add((String)skills.get("skillName"));
    	}
		return userSkills;
	}
	@Override
	public List<String> findSkillsForProject(Integer id) {
		List<Map<String, Object>> skillsForProject = projectSkillDAO.findSkillsByProjectId(id);
		List<String> projectSkills = new ArrayList<String>();
		for(Map<String, Object> skills: skillsForProject)	{
    		projectSkills.add((String)skills.get("skillName"));
    	}
		return projectSkills;
	}
	@Override
	public void saveSkillsForUser(Integer id, List<String> skillsList) throws SkillException {
		requireNonNull(userDAO.findById(id),"User with id: "+id+" doesn't exist. Please provide a valid user id.");
		List<UserSkill> userSkills = new ArrayList<UserSkill>();
		int skillCount=userSkillDAO.countByUserId(id);
		int skipCount = 0;//if the skill already exists
		String skillAlreadyExist = "";
		String invalidSkillName = "";
		for(String skillName: skillsList)	{
			UserSkill userSkill = new UserSkill();
			userSkill.setUser(userDAO.findById(id));
			
			Skill skill = skillDAO.findBySkillName(skillName);
			if(isNull(skill))	{
				invalidSkillName += skillName +" ";
				skipCount++;
				continue;
			}
				
			int skillExist = userSkillDAO.countByUserIdAndSkillId(id,skill.getId());
			if (skillExist != 0)	{
				skipCount++;
				skillAlreadyExist+=skillName+ " ";
				continue;
			}
			userSkill.setSkill(skill);
			
			int displayOrder =  skillsList.indexOf(skillName)+1-skipCount+skillCount;
			userSkill.setDisplayOrder(displayOrder);
			
			userSkills.add(userSkill);
		}
		userSkillDAO.save(userSkills);
		if(!invalidSkillName.isEmpty() && !skillAlreadyExist.isEmpty())	throw new SkillException("Invalid skill name(s): "+invalidSkillName.trim()
																						+". Please provide valid skill name(s)."+skillAlreadyExist+"already exist(s).");
		if(!invalidSkillName.isEmpty())	throw new SkillException("Invalid skill name(s): "+invalidSkillName.trim()
																		+". Please provide valid skill name(s).");
		if(!skillAlreadyExist.isEmpty())	throw new SkillException(skillAlreadyExist+"already exist(s).");
		
	}
	@Override
	public void saveSkillsForProject(Integer id, List<String> skillsList) throws SkillException {
		requireNonNull(projectDAO.findById(id),"Project with id: "+id+" doesn't exist. Please provide a valid project id.");
		List<ProjectSkill> projectSkills =new ArrayList<ProjectSkill>();
		int skillCount=projectSkillDAO.countByProjectId(id);
		int skipCount = 0;//if the skill already exists
		String skillAlreadyExist = "";
		String invalidSkillName = "";
		for(String skillName: skillsList)	{
			ProjectSkill projectSkill = new ProjectSkill();
			projectSkill.setProject(projectDAO.findById(id));
			
			Skill skill = skillDAO.findBySkillName(skillName);
			if(isNull(skill))	{
				invalidSkillName += skillName +" ";
				skipCount++;
				continue;
			}
			
			int skillExist = projectSkillDAO.countByProjectIdAndSkillId(id,skill.getId());
			if (skillExist != 0)	{
				skipCount++;
				skillAlreadyExist+=skillName+ " ";
				continue;
			}
			projectSkill.setSkill(skill);
			
			int displayOrder = skillsList.indexOf(skillName)+1-skipCount+skillCount;
			projectSkill.setDisplayOrder(displayOrder);
			
			projectSkills.add(projectSkill);
		}
		projectSkillDAO.save(projectSkills);	
		
		if(!invalidSkillName.isEmpty() && !skillAlreadyExist.isEmpty())	
					throw new SkillException("Invalid skill name(s): "+invalidSkillName.trim()
													+". Please provide valid skill name(s). And also "+skillAlreadyExist+"already exist(s).");
		if(!invalidSkillName.isEmpty())	throw new SkillException("Invalid skill name(s): "+invalidSkillName.trim()
													+". Please provide valid skill name(s).");
		if(!skillAlreadyExist.isEmpty())	throw new SkillException(skillAlreadyExist+"already exist(s).");

	}
}
