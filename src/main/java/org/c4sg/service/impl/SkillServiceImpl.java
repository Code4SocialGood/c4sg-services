package org.c4sg.service.impl;

import static java.util.Objects.isNull;
import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.c4sg.dao.ProjectDAO;
import org.c4sg.dao.ProjectSkillDAO;
import org.c4sg.dao.SkillDAO;
import org.c4sg.dao.UserDAO;
import org.c4sg.dao.UserSkillDAO;
import org.c4sg.dto.SkillDTO;
import org.c4sg.entity.Project;
import org.c4sg.entity.ProjectSkill;
import org.c4sg.entity.Skill;
import org.c4sg.entity.User;
import org.c4sg.entity.UserSkill;
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
    public List<SkillDTO> findSkillsbyCount() {
   		List<Object[]> skills = skillDAO.findSkillsbyCount();
   		List<SkillDTO> skillDTOS = new SkillMapper().getSkillDTOs(skills);
       	return skillDTOS;        
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
	public void saveSkillsForUser(Integer id, List<String> skillsList) {
		User user = userDAO.findById(id);
		requireNonNull(user,"User with id: "+id+" doesn't exist. Please provide a valid user id.");
		
		List<UserSkill> userSkills = new ArrayList<UserSkill>();
		List<Skill> skills = new ArrayList<Skill>();
		
		requireNonNull(skillsList,"Please provide the skills in display order.");
		for(String skillName: skillsList)	{
			UserSkill userSkill = new UserSkill();
			userSkill.setUser(user); //user
			
			Skill skill = skillDAO.findBySkillName(skillName);
			//user entered skills
			if(isNull(skill))	{
				skill = new Skill();
				skill.setSkillName(skillName);
				
				skills.add(skill); //new skill to add to Skill table
			}	
			userSkill.setSkill(skill); //skill
			int displayOrder =  skillsList.indexOf(skillName)+1; //displayOrder
			userSkill.setDisplayOrder(displayOrder);
			
			userSkills.add(userSkill);
		}
		
		skillDAO.save(skills);
		userSkillDAO.deleteByUserId(id);
		userSkillDAO.save(userSkills);				
	}
	
	@Override
	public void saveSkillsForProject(Integer id, List<String> skillsList) {
		Project project = projectDAO.findById(id);
		requireNonNull(project,"Project with id: "+id+" doesn't exist. Please provide a valid project id.");
		
		List<ProjectSkill> projectSkills =new ArrayList<ProjectSkill>();
		List<Skill> skills = new ArrayList<Skill>();
		
		requireNonNull(skillsList,"Please provide the skills in display order.");
		for(String skillName: skillsList)	{
			ProjectSkill projectSkill = new ProjectSkill();
			projectSkill.setProject(project); //project
			
			Skill skill = skillDAO.findBySkillName(skillName);
			//user entered skills
			if(isNull(skill))	{
				skill = new Skill();
				skill.setSkillName(skillName);
				
				skills.add(skill); //new skill to add to Skill table
			}	
			projectSkill.setSkill(skill); //skill
			int displayOrder = skillsList.indexOf(skillName)+1;
			projectSkill.setDisplayOrder(displayOrder); //displayOrder
		
			projectSkills.add(projectSkill);
		}
		
		skillDAO.save(skills);
		projectSkillDAO.deleteByProjectId(id);
		projectSkillDAO.save(projectSkills);
	}
}