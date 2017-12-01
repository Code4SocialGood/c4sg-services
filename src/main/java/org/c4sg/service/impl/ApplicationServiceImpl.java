package org.c4sg.service.impl;

import static java.util.Objects.requireNonNull;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.c4sg.constant.Constants;
import org.c4sg.dao.ApplicationDAO;
import org.c4sg.dao.OrganizationDAO;
import org.c4sg.dao.ProjectDAO;
import org.c4sg.dao.UserDAO;
import org.c4sg.dao.UserOrganizationDAO;
import org.c4sg.dto.ApplicantDTO;
import org.c4sg.dto.ApplicationDTO;
import org.c4sg.dto.ApplicationProjectDTO;
import org.c4sg.dto.EmailDTO;
import org.c4sg.dto.ProjectDTO;
import org.c4sg.entity.Application;
import org.c4sg.entity.Organization;
import org.c4sg.entity.Project;
import org.c4sg.entity.User;
import org.c4sg.entity.UserOrganization;
import org.c4sg.exception.BadRequestException;
import org.c4sg.exception.UserProjectException;
import org.c4sg.mapper.ApplicationMapper;
import org.c4sg.mapper.UserMapper;
import org.c4sg.mapper.converter.BooleanToStringConverter;
import org.c4sg.service.ApplicationService;
import org.c4sg.service.AsyncEmailService;
import org.c4sg.service.C4sgUrlService;
import org.c4sg.service.SkillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class ApplicationServiceImpl implements ApplicationService {
	
	@Autowired
	private AsyncEmailService asyncEmailService;
	
	@Autowired
    private ApplicationDAO applicationDAO;
	
	@Autowired
	private UserDAO userDAO;
	
	@Autowired
	private ProjectDAO projectDAO;
	
	@Autowired
	private UserOrganizationDAO userOrganizationDAO;
	
	@Autowired
	private ApplicationMapper applicationMapper;
	
	@Autowired
	private BooleanToStringConverter booleanToStringConverter;
	
	@Autowired
    private SkillService skillService;
	
	@Autowired
    private C4sgUrlService urlService;
	
	@Autowired
    private OrganizationDAO organizationDAO;
		
	@Override
	public List<ProjectDTO> getApplicationsByUser(Integer userId, String status){
		
		List<Application> applications = applicationDAO.findByUser_IdAndStatus(userId, status);
		return applicationMapper.getProjectDtosFromApplicationEntities(applications);
		
	}
	
	@Override
	public List<ApplicantDTO> getApplicants(Integer projectId) {
		
		List<Application> applications = applicationDAO.findByProject_Id(projectId);
		return applicationMapper.getApplicantDtosFromEntities(applications);
		
	}
	
	@Override
	public List<ApplicationProjectDTO> getApplicationsByOrgAndByApplicant(Integer userId, Integer nonProfitUserId, String status){
		
		//get org id from nonProfitUserId;
		List<UserOrganization> userOrganizations = userOrganizationDAO.findByUserId(nonProfitUserId);
		
		//get projects for the org
		List<Project> projects = new ArrayList<Project>();		
		for(UserOrganization userOrganization: userOrganizations){
			
			//list gets overwritten here becasue there should be only one organization for one nonprofit user
			projects = projectDAO.getProjectsByOrganization(userOrganization.getOrganization().getId(),"A");
		}
		
		//get applications by project and userId
		List<Application> applications = new ArrayList<Application>();
		Application application;
		for(Project project: projects){
			
			application = new Application();
			application = applicationDAO.findByUser_IdAndProject_IdAndStatus(userId, project.getId(), status);
			if(java.util.Objects.nonNull(application)){
				applications.add(application);
			}
						
		}		
			
		return applicationMapper.getApplicationProjectDtosFromEntities(applications);
	}
	
	
	@Override
	public ApplicationDTO createApplication(ApplicationDTO applicationDto){
				
		validateApplication(applicationDto);
		
		User user = userDAO.findById(applicationDto.getUserId());
		Project project = projectDAO.findById(applicationDto.getProjectId());
		Application application = applicationDAO.findByUser_IdAndProject_Id(applicationDto.getUserId(), applicationDto.getProjectId()); 
		if(java.util.Objects.nonNull(application)){
			isApplied(application, applicationDto.getStatus());
		}		
		application = new Application();
    	application.setUser(user);
    	application.setProject(project);
    	application.setStatus(applicationDto.getStatus());
    	application.setComment(applicationDto.getComment());
    	application.setResumeFlag(booleanToStringConverter.convert(applicationDto.getResumeFlag()));
    	application.setAppliedTime(applicationDto.getAppliedTime());
    	application.setCreatedTime(new Timestamp(Calendar.getInstance().getTime().getTime()));
    	//application.setAppliedTime(new Timestamp(Calendar.getInstance().getTime().getTime()));
        applicationDAO.save(application);
	    
        sendEmail(user, project, application);
        
        return applicationMapper.getApplicationDtoFromEntity(application);
		
	}
	
	@Override
	public ApplicationDTO updateApplication(ApplicationDTO applicationDto){
		
		validateApplication(applicationDto);
		
        Application application = applicationDAO.findByUser_IdAndProject_Id(applicationDto.getUserId(), applicationDto.getProjectId()); 
        isApplied(application, applicationDto.getStatus());
        application.setStatus(applicationDto.getStatus());
        application.setComment(applicationDto.getComment());
    	application.setResumeFlag(booleanToStringConverter.convert(applicationDto.getResumeFlag()));
    	if(applicationDto.getStatus().equals("C")){
    		application.setAcceptedTime(new Timestamp(Calendar.getInstance().getTime().getTime()));
    	}else if(applicationDto.getStatus().equals("D")){
    	    application.setDeclinedTime(new Timestamp(Calendar.getInstance().getTime().getTime()));
    	}
    	application.setUpdatedTime(new Timestamp(Calendar.getInstance().getTime().getTime()));
        applicationDAO.save(application);
	    
        User user = userDAO.findById(applicationDto.getUserId());
		Project project = projectDAO.findById(applicationDto.getProjectId());
        sendEmail(user, project, application);
        
        return applicationMapper.getApplicationDtoFromEntity(application);
	}
	
	@Override
	public Long deleteApplication(Integer id){
		return applicationDAO.deleteById(id);
	}
	
	
	private void validateApplication(ApplicationDTO application){
		
		User user = userDAO.findById(application.getUserId());
        requireNonNull(user, "Invalid User Id");
        Project project = projectDAO.findById(application.getProjectId());
        requireNonNull(project, "Invalid Project Id");
        if (application.getStatus() == null || (!application.getStatus().equals("A") && !application.getStatus().equals("C") && !application.getStatus().equals("D"))) {
        	throw new BadRequestException("Invalid Project Status");
        } 
	}
	
	private void isApplied(Application application, String status) throws UserProjectException {

    	//Application application = applicationDAO.findByUser_IdAndProject_Id(userId, projectId);        	
    	//requireNonNull(application, "Invalid operation");
    	
    		if(status.equals("A") && java.util.Objects.nonNull(application.getAppliedTime()))
        	{
        		throw new UserProjectException("Already applied for the porject.");
        		
        	} else if (status.equals("C") && java.util.Objects.nonNull(application.getAcceptedTime()) && (java.util.Objects.isNull(application.getDeclinedTime()) ||
                    (java.util.Objects.nonNull(application.getDeclinedTime()) && application.getAcceptedTime().after(application.getDeclinedTime())))){
        		
        		throw new UserProjectException("Already accepted for the project.");	
        		
        	} else if (status.equals("D") && java.util.Objects.nonNull(application.getDeclinedTime()) && (java.util.Objects.isNull(application.getAcceptedTime()) ||
					(java.util.Objects.nonNull(application.getAcceptedTime()) && application.getAcceptedTime().before(application.getDeclinedTime())))){
        		
        		throw new UserProjectException("Already declined for the project.");
        	}
    	   	
    }
	
	@Async
    private void sendEmail(User user, Project project, Application application) {

        Integer orgId = project.getOrganization().getId();
        List<User> users = userDAO.findByOrgId(orgId);
        if (users != null && !users.isEmpty()) {
        	
        	List<String> userSkills = skillService.findSkillsForUser(user.getId());
        	User orgUser = users.get(0);
        	Organization org = organizationDAO.findOne(project.getOrganization().getId());
        	
        	if (application.getStatus().equals("A")) {
        		// send email to organization
        		Map<String, Object> contextOrg = new HashMap<String, Object>();
        		contextOrg.put("user", user);
        		contextOrg.put("skills", userSkills);
        		contextOrg.put("project", project);
        		contextOrg.put("projectLink", urlService.getProjectUrl(project.getId()));
        		contextOrg.put("userLink", urlService.getUserUrl(user.getId()));
        		contextOrg.put("application", application);
        		asyncEmailService.sendWithContext(Constants.C4SG_ADDRESS, orgUser.getEmail(),user.getEmail(), Constants.SUBJECT_APPLICATION_ORGANIZATION, Constants.TEMPLATE_APPLICAITON_ORGANIZATION, contextOrg);
        	
        		// send email to volunteer        		
       			Map<String, Object> contextVolunteer = new HashMap<String, Object>();
       			contextVolunteer.put("org", org);
       			contextVolunteer.put("orgUser", orgUser);
       			contextVolunteer.put("project", project);
       			contextVolunteer.put("projectLink", urlService.getProjectUrl(project.getId()));
       			asyncEmailService.sendWithContext(Constants.C4SG_ADDRESS, user.getEmail(), orgUser.getEmail(), Constants.SUBJECT_APPLICATION_VOLUNTEER, Constants.TEMPLATE_APPLICAITON_VOLUNTEER, contextVolunteer);
        	
        		System.out.println("Application email sent: Project=" + project.getId() + " ; ApplicantEmail=" + user.getEmail() + " ; OrgEmail=" + orgUser.getEmail());
        	
        	} else if (application.getStatus().equals("C")) {
				// send email to organization
				Map<String, Object> contextOrg = new HashMap<String, Object>();
				contextOrg.put("user", user);
				contextOrg.put("skills", userSkills);
				contextOrg.put("project", project);
				contextOrg.put("projectLink", urlService.getProjectUrl(project.getId()));
				contextOrg.put("userLink", urlService.getUserUrl(user.getId()));
				contextOrg.put("application", application);
				asyncEmailService.sendWithContext(Constants.C4SG_ADDRESS, orgUser.getEmail(),user.getEmail(), Constants.SUBJECT_APPLICATION_ACCEPT_ORGANIZATION, Constants.TEMPLATE_APPLICAITON_ACCEPT_ORGANIZATION, contextOrg);
				System.out.println("Application email sent to org user: Project=" + project.getId() + " ; OrgUserEmail=" + orgUser.getEmail());

        		// send email to volunteer
       			Map<String, Object> contextVolunteer = new HashMap<String, Object>();
       			contextVolunteer.put("org", org);
       			contextVolunteer.put("orgUser", orgUser);
       			contextVolunteer.put("project", project);
       			contextVolunteer.put("projectLink", urlService.getProjectUrl(project.getId()));
       			asyncEmailService.sendWithContext(Constants.C4SG_ADDRESS, user.getEmail(), orgUser.getEmail(), Constants.SUBJECT_APPLICATION_ACCEPT, Constants.TEMPLATE_APPLICAITON_ACCEPT, contextVolunteer);
        		System.out.println("Application email sent to volunteer: Project=" + project.getId() + " ; ApplicantEmail=" + user.getEmail());
   
        	} else if (application.getStatus().equals("D")) {
        		// send email to volunteer
       			Map<String, Object> contextVolunteer = new HashMap<String, Object>();
       			contextVolunteer.put("org", org);
       			contextVolunteer.put("orgUser", orgUser);
       			contextVolunteer.put("project", project);
       			contextVolunteer.put("projectLink", urlService.getProjectUrl(project.getId()));
       			asyncEmailService.sendWithContext(Constants.C4SG_ADDRESS, user.getEmail(), orgUser.getEmail(), Constants.SUBJECT_APPLICATION_DECLINE, Constants.TEMPLATE_APPLICAITON_DECLINE, contextVolunteer);        	
        		System.out.println("Application email sent: Project=" + project.getId() + " ; ApplicantEmail=" + user.getEmail());

        	} else if (application.getStatus().equals("B")) {
        		// do nothing
        	}
        }
    }
	
	

}
