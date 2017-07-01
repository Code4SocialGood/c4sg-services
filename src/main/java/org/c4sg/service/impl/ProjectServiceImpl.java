package org.c4sg.service.impl;

import static java.util.Objects.requireNonNull;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;

import org.c4sg.controller.UserController;
import org.c4sg.dao.OrganizationDAO;
import org.c4sg.dao.ProjectDAO;
import org.c4sg.dao.ProjectSkillDAO;
import org.c4sg.dao.UserDAO;
import org.c4sg.dao.UserProjectDAO;
import org.c4sg.dto.CreateProjectDTO;
import org.c4sg.dto.ProjectDTO;
import org.c4sg.entity.Organization;
import org.c4sg.entity.Project;
import org.c4sg.entity.User;
import org.c4sg.entity.UserProject;
import org.c4sg.exception.BadRequestException;
import org.c4sg.exception.ProjectServiceException;
import org.c4sg.exception.UserProjectException;
import org.c4sg.mapper.ProjectMapper;
import org.c4sg.service.AsyncEmailService;
import org.c4sg.service.ProjectService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ProjectServiceImpl implements ProjectService {
	private static Logger LOGGER = LoggerFactory.getLogger(UserController.class);
	
    @Autowired
    private ProjectDAO projectDAO;

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private UserProjectDAO userProjectDAO;
    
    @Autowired
    private ProjectSkillDAO projectSkillDAO;

    @Autowired
    private ProjectMapper projectMapper;

    @Autowired
    private AsyncEmailService asyncEmailService;
       
    @Autowired
    private OrganizationDAO organizationDAO;
    
    private static final String FROM_EMAIL = "info@code4socialgood.org";
    private static final String SUBJECT_ORGANIZATION = "You received an application from Code for Social Good";
    private static final String BODY_ORGANIZATION= "You received an application from Code for Social Good. " 
    				+ "Please login to the dashboard to review the application.";
    private static final String SUBJECT_APPLICANT = "You submitted an application from Code for Social Good";
    private static final String BODY_APPLICANT= "You submitted an application from Code for Social Good. " 
    				+ "Organization is notified to review your application and contact you.";
    private static final String SUBJECT_NOTIFICATION = "Code for Social Good: New Project Notification";
    private static final String BODY_NOTIIFCATION= "You have registered to recieve notification on new projects.\n" 
    				+ "The following new project has been created:\n" + "http://dev.code4socialgood.org/project/view/";

    public void save(ProjectDTO projectDTO) {
    	Project project = projectMapper.getProjectEntityFromDto(projectDTO);
        projectDAO.save(project);
    }

    public List<ProjectDTO> findProjects() {
        List<Project> projects = projectDAO.findAllByOrderByIdDesc();
        return projectMapper.getDtosFromEntities(projects);
    }

    public ProjectDTO findById(int id) {
        return projectMapper.getProjectDtoFromEntity(projectDAO.findById(id));
    }

    public ProjectDTO findByName(String name) {
        return projectMapper.getProjectDtoFromEntity(projectDAO.findByName(name));
    }

    public Page<ProjectDTO> findByKeyword(String keyWord, List<Integer> skills, String status, String remote,Integer page, Integer size) {
    	Page<Project> projectPages=null;
		List<Project> projects=null;
    	if (page==null){
    		page=0;
    	}		
		if (size==null){
	    	if(skills != null) {
	    		projects = projectDAO.findByKeywordAndSkill(keyWord, skills, status, remote);
	    	} else {
	    		projects = projectDAO.findByKeyword(keyWord, status, remote);
	    	}
			projectPages=new PageImpl<Project>(projects);	    	
		}else{
			Pageable pageable=new PageRequest(page,size);
	    	if(skills != null) {
	    		projectPages = projectDAO.findByKeywordAndSkill(keyWord, skills, status, remote,pageable);
	    	} else {
	    		projectPages = projectDAO.findByKeyword(keyWord, status, remote,pageable);
	    	}			
		}		
        return projectPages.map(p -> projectMapper.getProjectDtoFromEntity(p));
    }
    
    @Override
    public List<ProjectDTO> findByUser(Integer userId, String userProjectStatus) throws ProjectServiceException {
    	
    	List<Project> projects = projectDAO.findByUserIdAndUserProjectStatus(userId, userProjectStatus);  	
    	return projectMapper.getDtosFromEntities(projects);
    }

    @Override
    public List<ProjectDTO> findByOrganization(Integer orgId, String projectStatus) {
        List<Project> projects = projectDAO.getProjectsByOrganization(orgId, projectStatus);
        return projectMapper.getDtosFromEntities(projects);
    }

    public ProjectDTO createProject(CreateProjectDTO createProjectDTO) {
        Project project = projectDAO.findByNameAndOrganizationId(
        			createProjectDTO.getName(), createProjectDTO.getOrganizationId());
        
        if (project != null) {
            System.out.println("Project already exist.");
        } else {
            project = projectDAO.save(
            		projectMapper.getProjectEntityFromCreateProjectDto(createProjectDTO));

            // Updates projectUpdateTime for the organization
            Organization localOrgan = project.getOrganization(); 
            localOrgan.setProjectUpdatedTime(new Timestamp(Calendar.getInstance().getTime().getTime())); 
            organizationDAO.save(localOrgan);

            // Notify volunteer users of new project
            List<User> notifyUsers = userDAO.findByNotify();
            for (int i=0; i<notifyUsers.size(); i++) {
            	String to = notifyUsers.get(i).getEmail();
              	String subject = SUBJECT_NOTIFICATION;
            	String body = BODY_NOTIIFCATION + project.getId();
            	asyncEmailService.send(FROM_EMAIL, to, subject, body);
            }
        }

        return projectMapper.getProjectDtoFromEntity(project);
    }

    public ProjectDTO updateProject(ProjectDTO project) {
        Project localProject = projectDAO.findById(project.getId());
        
        if (localProject != null) {
            localProject = projectDAO.save(projectMapper.getProjectEntityFromDto(project));
        } else {
            System.out.println("Project does not exist.");
        }

        return projectMapper.getProjectDtoFromEntity(localProject);
    }

    @Override
    public ProjectDTO saveUserProject(Integer userId, Integer projectId, String userProjectStatus ) {

        User user = userDAO.findById(userId);
        requireNonNull(user, "Invalid User Id");
        Project project = projectDAO.findById(projectId);
        requireNonNull(project, "Invalid Project Id");
        if (userProjectStatus == null || (!userProjectStatus.trim().equalsIgnoreCase("A") && !userProjectStatus.trim().equalsIgnoreCase("B"))) {
        	throw new BadRequestException("Invalid Project Status");
        }
        
        else if(userProjectStatus.trim().equalsIgnoreCase("A")){
	        isUserAppliedPresent(userId, projectId);
	        UserProject userProject = new UserProject();
	        userProject.setUser(user);
	        userProject.setProject(project);
	        userProject.setStatus("A");
	        apply(user, project);
	        userProjectDAO.save(userProject);
	        }
        else if(userProjectStatus.trim().equalsIgnoreCase("B")){
        	isBookmarkPresent(userId, projectId);  
            UserProject userProject = new UserProject();
            userProject.setUser(user);
            userProject.setProject(project);
            userProject.setStatus("B");
            userProjectDAO.save(userProject);     	
        }

        return projectMapper.getProjectDtoFromEntity(project);
    }

    public void deleteProject(int id) throws UserProjectException  {
        Project localProject = projectDAO.findById(id);

        if (localProject != null) {
        	// TODO delete image from S3 by frontend
        	userProjectDAO.deleteByProjectStatus(new Integer(id),"B");        	
        	projectSkillDAO.deleteByProjectId(id);            	
            projectDAO.deleteProject(id);
        } else {
            System.out.println("Project does not exist.");
        }
    }
    
    private void apply(User user, Project project) {

        Integer orgId = project.getOrganization().getId();
        String orgEmail = userDAO.findByOrgId(orgId).get(0).getEmail();
        asyncEmailService.send(FROM_EMAIL, orgEmail, SUBJECT_ORGANIZATION, BODY_ORGANIZATION);

        String userEmail = user.getEmail();
        asyncEmailService.send(FROM_EMAIL, userEmail, SUBJECT_APPLICANT, BODY_APPLICANT);
        
    	System.out.println("Application email sent: Project=" + project.getId() 
    		+ " ; Applicant=" + user.getId() + " ; OrgEmail=" + orgEmail + " ; ApplicantEmail=" + userEmail);
    }
    
    private void isBookmarkPresent(Integer userId, Integer projectId) {
    	List<UserProject> userProjects = userProjectDAO.findByUser_IdAndProject_IdAndStatus(userId, projectId, "B");
    	
    	requireNonNull(userProjects, "Invalid operation");
    	for(UserProject userProject : userProjects)
    	{
    		if(userProject.getStatus().equals("B"))
        	{
        		throw new UserProjectException("Project is already bookmarked");
        	}
    	}    	
    }
    
    private void isUserAppliedPresent(Integer userId, Integer projectId) throws UserProjectException {

    	List<UserProject> userProjects = userProjectDAO.findByUser_IdAndProject_IdAndStatus(userId, projectId, "A");
    	
    	requireNonNull(userProjects, "Invalid operation");
    	for(UserProject userProject : userProjects)
    	{
    		if(userProject.getStatus().equals("A"))
        	{
        		throw new UserProjectException("Project is already applied for");
        	}
    	}    	
    }
    
	@Override
	public void saveImage(Integer id, String imgUrl) {
		projectDAO.updateImage(imgUrl, id);
	}
}
