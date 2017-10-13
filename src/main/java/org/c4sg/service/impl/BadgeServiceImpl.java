package org.c4sg.service.impl;

import static java.util.Objects.requireNonNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.c4sg.dao.BadgeDAO;
import org.c4sg.dao.OrganizationDAO;
import org.c4sg.dao.ProjectDAO;
import org.c4sg.dao.UserDAO;
import org.c4sg.dto.HeroDTO;
import org.c4sg.entity.Badge;
import org.c4sg.entity.Bookmark;
import org.c4sg.entity.Organization;
import org.c4sg.entity.Project;
import org.c4sg.entity.User;
import org.c4sg.exception.UserProjectException;
import org.c4sg.mapper.BadgeMapper;
import org.c4sg.service.AsyncEmailService;
import org.c4sg.service.BadgeService;
import org.c4sg.service.C4sgUrlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.c4sg.constant.Constants;

@Service
public class BadgeServiceImpl implements BadgeService {
	@Autowired
    private UserDAO userDAO;
	
	@Autowired
    private ProjectDAO projectDAO;
	
	@Autowired
    private BadgeDAO badgeDAO;
	
	@Autowired
    private BadgeMapper badgeMapper;
	
	@Autowired
    private C4sgUrlService urlService;
	
	@Autowired
    private OrganizationDAO organizationDAO;
	
	@Autowired
    private AsyncEmailService asyncEmailService;
	
	@Override
	public Badge saveBadge(Integer userId, Integer projectId) {
		/* Volunteer Recognition  - Give badge to the volunteer(accepted applicant)
		 * Send out email to the volunteer
		 * Save it to the database
		*/
		User user = userDAO.findById(userId);
        requireNonNull(user, "Invalid User Id");
        Project project = projectDAO.findById(projectId);
        requireNonNull(project, "Invalid Project Id");
        
        Integer orgId = project.getOrganization().getId();
        Organization org = organizationDAO.findOne(orgId);
        isBadgeGiven(userId, projectId);        
    	Badge heroBadge = new Badge();   
    	heroBadge.setUser(user);
    	heroBadge.setProject(project);
        heroBadge = badgeDAO.save(heroBadge);		
        		        
        //send email to the volunteer
        Map<String, Object> contextVolunteer = new HashMap<String, Object>();
		contextVolunteer.put("heroesLink", urlService.getHeroUrl());
		contextVolunteer.put("org", org);
		contextVolunteer.put("project", project);
		contextVolunteer.put("projectLink", urlService.getProjectUrl(project.getId()));
		asyncEmailService.sendWithContext(Constants.C4SG_ADDRESS, user.getEmail(), org.getContactEmail(), Constants.SUBJECT_HERO_USER, Constants.TEMPLATE_HERO_USER, contextVolunteer);   
		
		System.out.println("Hero Badge email sent: Project=" + project.getId() + " ; ApplicantEmail=" + user.getEmail());
        return heroBadge;
	}

	@Override
	public List<HeroDTO> getBadges() {
		// Get heroes from the Badge table
		List<Object[]>  heroes = badgeDAO.findHeroes();
		List<HeroDTO> heroDTOs = badgeMapper.getHeroDTOs(heroes);
		return heroDTOs;
	}
	
	private void isBadgeGiven(Integer userId, Integer projectId) throws UserProjectException {
    	Badge badge = badgeDAO.findByUser_IdAndProject_Id(userId, projectId);    	
    	if(java.util.Objects.nonNull(badge) && badge.getUser().getId().equals(userId) && badge.getProject().getId().equals(projectId))	{
    		throw new UserProjectException("Record already exist");
        }  	
    }
}
