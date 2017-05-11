package org.c4sg.service.impl;

import static java.util.Objects.nonNull;
import static java.util.Objects.requireNonNull;
import static org.c4sg.constant.Directory.PROJECT_UPLOAD;
import static org.c4sg.constant.Format.IMAGE;

import java.io.File;
import java.sql.Date;
import java.util.Calendar;
import java.util.List;

import org.c4sg.dao.OrganizationDAO;
import org.c4sg.dao.ProjectDAO;
import org.c4sg.dao.ProjectSkillDAO;
import org.c4sg.dao.UserDAO;
import org.c4sg.dao.UserProjectDAO;
import org.c4sg.dto.CreateProjectDTO;
import org.c4sg.dto.ProjectDTO;
import org.c4sg.dto.UserDTO;
import org.c4sg.entity.Organization;
import org.c4sg.entity.Project;
import org.c4sg.entity.User;
import org.c4sg.entity.UserProject;
import org.c4sg.exception.ProjectServiceException;
import org.c4sg.exception.UserProjectException;
import org.c4sg.mapper.ProjectMapper;
import org.c4sg.service.AsyncEmailService;
import org.c4sg.service.OrganizationService;
import org.c4sg.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectServiceImpl implements ProjectService {

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

    public List<ProjectDTO> findByKeyword(String keyWord, List<Integer> skills) {
    	long skillCount=0;
    	if (skills != null) skillCount=skills.size(); 
        List<Project> projects = projectDAO.findByKeyword(keyWord, skills, skillCount);
        return projectMapper.getDtosFromEntities(projects);
    }
    
    @Override
    public List<ProjectDTO> findByUser(Integer userId, String userProjectStatus) throws ProjectServiceException {
    	
    	List<Project> projects = null;
    	
    	// If status is not set, search by user ID only
    	if ((userProjectStatus == null) || userProjectStatus.isEmpty()) {
    		projects = projectDAO.findByUserId(userId);
    	} else {
    		projects = projectDAO.findByUserIdAndUserProjectStatus(userId, userProjectStatus);
    	}
    	
    	return projectMapper.getDtosFromEntities(projects);
    }

    @Override
    public List<ProjectDTO> findByOrganization(Integer orgId) {
        List<Project> projects = projectDAO.getProjectsByOrganization(orgId);
        if (projects == null || projects.size() == 0) {
            System.out.println("No Project available for the provided organization");
        }
        return projectMapper.getDtosFromEntities(projects);
    }

    public ProjectDTO createProject(CreateProjectDTO createProjectDTO) {
        Project localProject = projectDAO.findByNameAndOrganizationId(
        			createProjectDTO.getName(), createProjectDTO.getOrganizationId());
        
        if (localProject != null) {
            System.out.println("Project already exist.");
        } else {
            localProject = projectDAO.save(
            		projectMapper.getProjectEntityFromCreateProjectDto(createProjectDTO));

            // Updates projectUpdateTime for the organization
            Organization localOrgan = localProject.getOrganization(); 
            localOrgan.setProjectUpdatedTime(new Date(Calendar.getInstance().getTime().getTime())); 
            organizationDAO.save(localOrgan);
            //Date currentTime = new Date(Calendar.getInstance().getTime().getTime());
            //Integer organizationId = organizationDAO.updateProjectUpdatedTime(currentTime, createProjectDTO.getOrganizationId());
        }

        return projectMapper.getProjectDtoFromEntity(localProject);
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
    public ProjectDTO saveUserProject(Integer userId, Integer projectId) {
        User user = userDAO.findById(userId);
        requireNonNull(user, "Invalid User Id");
        Project project = projectDAO.findById(projectId);
        requireNonNull(project, "Invalid Project Id");
        isUserAppliedPresent(userId, projectId);
        UserProject userProject = new UserProject();
        userProject.setUser(user);
        userProject.setProject(project);
        apply(user, project);
        userProjectDAO.save(userProject);

        return projectMapper.getProjectDtoFromEntity(project);
    }

    public void deleteProject(int id) throws UserProjectException  {
        Project localProject = projectDAO.findById(id);

        if (localProject != null) {
        	
        	    File image = new File(getImageUploadPath(id));        	
        		boolean del = image.delete();        		  	
        		if (!del) {        		
        			 throw new UserProjectException("File does not exist.");   
        		}
        
        	userProjectDAO.updateStatus(new Integer(id),"B");        	
        	projectSkillDAO.deleteByProjectId(id);            	
            projectDAO.deleteProject(id,"D");
        } else {
            System.out.println("Project does not exist.");
        }
    }
    
    private void apply(User user, Project project) {
        String from = "code4socialgood@code4socialgood.com";
        String orgEmail = project.getOrganization().getContactEmail();
        String orgSubject = "You received an application from Code for Social Good";
        String orgText = "You received an application from Code for Social Good. " +
                "Please login to the dashboard to review the application.";
        asyncEmailService.send(from, orgEmail, orgSubject, orgText);

        String userEmail = user.getEmail();
        String userSubject = "You submitted an application from Code for Social Good";
        String userText = "You submitted an application from Code for Social Good. " +
                "Organization is notified to review your application and contact you.";
        asyncEmailService.send(from, userEmail, userSubject, userText);
    }

    public String getImageUploadPath(Integer projectId) {
        return PROJECT_UPLOAD.getValue() + File.separator + projectId + IMAGE.getValue();
    }
       
    
 
    
    @Override
    public void saveUserProjectBookmark(Integer userId, Integer projectId) {
        
    	User user = userDAO.findById(userId);
        requireNonNull(user, "Invalid User Id");
        
        Project project = projectDAO.findById(projectId);
        requireNonNull(project, "Invalid Project Id");
        
        isBookmarkPresent(userId, projectId);
        
        UserProject userProject = new UserProject();
        userProject.setUser(user);
        userProject.setProject(project);
        userProject.setStatus("B");
        //apply(user, project);
        userProjectDAO.save(userProject);

        //return projectMapper.getProjectDtoFromEntity(project);
    }
    
    private void isBookmarkPresent(Integer userId, Integer projectId)
    {
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
        UserProject userProject = userProjectDAO.findByUser_IdAndProject_Id(userId, projectId);
        if (nonNull(userProject)) {
            throw new UserProjectException("The user already exists in that project");
        }
    }
}
