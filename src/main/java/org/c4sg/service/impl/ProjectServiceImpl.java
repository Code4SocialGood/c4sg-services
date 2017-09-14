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
import org.c4sg.dao.BookmarkDAO;
import org.c4sg.dao.OrganizationDAO;
import org.c4sg.dao.ProjectDAO;
import org.c4sg.dao.ProjectSkillDAO;
import org.c4sg.dao.UserDAO;
import org.c4sg.dao.UserProjectDAO;
import org.c4sg.dto.CreateProjectDTO;
import org.c4sg.dto.JobTitleDTO;
import org.c4sg.dto.ProjectDTO;
import org.c4sg.entity.Application;
import org.c4sg.entity.Bookmark;
import org.c4sg.entity.JobTitle;
import org.c4sg.entity.Organization;
import org.c4sg.entity.Project;
import org.c4sg.entity.User;
import org.c4sg.entity.UserProject;
import org.c4sg.exception.BadRequestException;
import org.c4sg.exception.ProjectServiceException;
import org.c4sg.exception.UserProjectException;
import org.c4sg.mapper.ProjectMapper;
import org.c4sg.service.AsyncEmailService;
import org.c4sg.service.C4sgUrlService;
import org.c4sg.service.ProjectService;
import org.c4sg.service.SkillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
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
    private ApplicationDAO applicationDAO;
    
    @Autowired
    private BookmarkDAO bookmarkDAO;
    
    @Autowired
    private ProjectSkillDAO projectSkillDAO;

    @Autowired
    private ProjectMapper projectMapper;
    
    @Autowired
    private SkillService skillService;

    @Autowired
    private AsyncEmailService asyncEmailService;
       
    @Autowired
    private OrganizationDAO organizationDAO;
    
    @Autowired
    private C4sgUrlService urlService;
    
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

    public Page<ProjectDTO> search(String keyWord, List<Integer> jobTitles, List<Integer> skills, String status, String remote,Integer page, Integer size) {
    	Page<Project> projectPages = null;
		List<Project> projects = null;
		
    	if (page==null){
    		page=0;
    	}		
    	
		if (size == null){	    	
			if(skills != null && jobTitles != null) {
				projects = projectDAO.findByKeywordAndJobAndSkill(keyWord, jobTitles, skills, status, remote);
			} else if(skills != null) {
				projects = projectDAO.findByKeywordAndSkill(keyWord, skills, status, remote);
			} else if(jobTitles != null) {
				projects = projectDAO.findByKeywordAndJob(keyWord, jobTitles, status, remote);
	    	} else {
	    		projects = projectDAO.findByKeyword(keyWord,  status, remote);
	    	}    	
	    		    	
			projectPages=new PageImpl<Project>(projects);	    	
		} else{
			Pageable pageable = new PageRequest(page,size);
			if(skills != null && jobTitles != null) {
				projectPages = projectDAO.findByKeywordAndJobAndSkill(keyWord, jobTitles, skills, status, remote, pageable);
			} else if(skills != null) {
				projectPages = projectDAO.findByKeywordAndSkill(keyWord, skills, status, remote, pageable);
			} else if(jobTitles != null) {
				projectPages = projectDAO.findByKeywordAndJob(keyWord, jobTitles, status, remote, pageable);
	    	} else {
	    		projectPages = projectDAO.findByKeyword(keyWord,  status, remote, pageable);
	    	}  		
		}		
		
        return projectPages.map(p -> projectMapper.getProjectDtoFromEntity(p));
    }
    
      

    @Override
    public List<ProjectDTO> findByOrganization(Integer orgId, String projectStatus) {
        List<Project> projects = projectDAO.getProjectsByOrganization(orgId, projectStatus);
        
        // There should always be a new project for an organization. If new project doesn't exist, create one
        if (projectStatus != null && projectStatus.equals("N")) {
        	if ((projects == null) || projects.size() == 0) {        		
            	Project project = new Project();
            	project.setOrganization(organizationDAO.findOne(orgId));
            	project.setRemoteFlag("Y");
            	project.setStatus("N");        	
            	projectDAO.save(project);
            	projects = projectDAO.getProjectsByOrganization(orgId, projectStatus);
        	}
        }
        
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
        }

        return projectMapper.getProjectDtoFromEntity(project);
    }

    public ProjectDTO updateProject(ProjectDTO projectDTO) {
    	
        Project project = projectDAO.findById(projectDTO.getId());
        
        if (project == null) {
        	System.out.println("Project does not exist.");
        } else {
        	String oldStatus = project.getStatus();
        	project = projectDAO.save(projectMapper.getProjectEntityFromDto(projectDTO));
        	String newStatus = project.getStatus();
        	
            // Notify volunteer users of new project
        	if (oldStatus.equals(Constants.ORGANIZATION_STATUS_NEW) && newStatus.equals(Constants.ORGANIZATION_STATUS_ACTIVE)) {
        		List<User> notifyUsers = userDAO.findByNotify();
        		if (notifyUsers != null && !notifyUsers.isEmpty()) {
        			for (int i=0; i<notifyUsers.size(); i++) {
        				String toAddress = notifyUsers.get(i).getEmail();
        				Map<String, Object> context = new HashMap<String, Object>();
        				context.put("project", projectDTO);         	
        				context.put("projectLink", urlService.getProjectUrl(projectDTO.getId()));
        				asyncEmailService.sendWithContext(Constants.C4SG_ADDRESS, toAddress, "",Constants.SUBJECT_NEW_PROJECT_NOTIFICATION, Constants.TEMPLATE_NEW_PROJECT_NOTIFICATION, context);
        			}
        			System.out.println("New project email sent: Project=" + projectDTO.getId());
        		} 
        	}
        } 

        return projectMapper.getProjectDtoFromEntity(project);
    }
    
    public List<JobTitleDTO> findJobTitles() {
		List<JobTitle> jobTitles = projectDAO.findJobTitles();
		return projectMapper.getJobTitleDtosFromEntities(jobTitles);
	}
	
	@Override
	public void saveImage(Integer id, String imgUrl) {
				
		projectDAO.updateImage(imgUrl, id);
	}
    
    
    /*---------------------------------------User Project code -----------------------------------------------------------*/
    
    @Override
    public List<ProjectDTO> findByUser(Integer userId, String userProjectStatus) throws ProjectServiceException {
    	
    	//List<Project> projects = projectDAO.findByUserIdAndUserProjectStatus(userId, userProjectStatus); 
    	
    	List<ProjectDTO> projectDtos = new ArrayList<ProjectDTO>();
    	
    	if(userProjectStatus.equals("A")){
    		List<Application> applications = applicationDAO.findByUser_IdAndStatus(userId, userProjectStatus);
    		projectDtos = projectMapper.getDtosFromApplicationEntities(applications);
    	}else if(userProjectStatus.equals("B")){
    		List<Bookmark> bookmarks = bookmarkDAO.findByUser_Id(userId);
    		projectDtos = projectMapper.getDtosFromBookmarkEntities(bookmarks);
    	}    	
    	return projectDtos;
    }  

    /*@Override
    public ProjectDTO saveUserProject(Integer userId, Integer projectId, String status ) {

        User user = userDAO.findById(userId);
        requireNonNull(user, "Invalid User Id");
        Project project = projectDAO.findById(projectId);
        requireNonNull(project, "Invalid Project Id");
        if (status == null || (!status.equals("A") && !status.equals("B") && !status.equals("C") && !status.equals("D"))) {
        	throw new BadRequestException("Invalid Project Status");
        } else {
	        isRecordExist(userId, projectId, status);
	        UserProject userProject = new UserProject();
	        userProject.setUser(user);
	        userProject.setProject(project);
	        userProject.setStatus(status);
	        userProjectDAO.save(userProject);
	    }
        
        sendEmail(user, project, status);
        
        return projectMapper.getProjectDtoFromEntity(project);
    }*/
    
    @Override
    public ProjectDTO saveApplication(Integer userId, Integer projectId, String status, String comment, String resumeFlag){
    	
    	User user = userDAO.findById(userId);
        requireNonNull(user, "Invalid User Id");
        Project project = projectDAO.findById(projectId);
        requireNonNull(project, "Invalid Project Id");
        if (status == null || (!status.equals("A") && !status.equals("C") && !status.equals("D"))) {
        	throw new BadRequestException("Invalid Project Status");
        } else {
        	isApplied(userId, projectId, status);
	        Application application = new Application();
	        application.setUser(user);
	        application.setProject(project);
	        application.setStatus(status);
	        application.setComment(comment);
	        application.setResumeFlag(resumeFlag);
	        applicationDAO.save(application);
	        //userProjectDAO.save(userProject);
	    }
        
        sendEmail(user, project, status);
        
        return projectMapper.getProjectDtoFromEntity(project);
    } 
    
    @Override
    public ProjectDTO saveBookmark(Integer userId, Integer projectId){
    	
    	User user = userDAO.findById(userId);
        requireNonNull(user, "Invalid User Id");
        Project project = projectDAO.findById(projectId);
        requireNonNull(project, "Invalid Project Id");
       
        isBookmarked(userId, projectId);
	    Bookmark bookmark = new Bookmark();
	    bookmark.setUser(user);
	    bookmark.setProject(project);	        
	    bookmarkDAO.save(bookmark);
	        //userProjectDAO.save(userProject);
	    
        
        //sendEmail(user, project, status);
        
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
	
	/*------------------------------------ private methods-------------------------------------------*/
	
	@Async
    private void sendEmail(User user, Project project, String status) {

        Integer orgId = project.getOrganization().getId();
        List<User> users = userDAO.findByOrgId(orgId);
        if (users != null && !users.isEmpty()) {
        	
        	List<String> userSkills = skillService.findSkillsForUser(user.getId());
        	User orgUser = users.get(0);
        	Organization org = organizationDAO.findOne(project.getOrganization().getId());
        	
        	if (status.equals("A")) {
        		// send email to organization
        		Map<String, Object> contextOrg = new HashMap<String, Object>();
        		contextOrg.put("user", user);
        		contextOrg.put("skills", userSkills);
        		contextOrg.put("project", project);
        		contextOrg.put("projectLink", urlService.getProjectUrl(project.getId()));
        		contextOrg.put("userLink", urlService.getUserUrl(user.getId()));
        		asyncEmailService.sendWithContext(Constants.C4SG_ADDRESS, orgUser.getEmail(),user.getEmail(), Constants.SUBJECT_APPLICAITON_ORGANIZATION, Constants.TEMPLATE_APPLICAITON_ORGANIZATION, contextOrg);
        	
        		// send email to volunteer        		
       			Map<String, Object> contextVolunteer = new HashMap<String, Object>();
       			contextVolunteer.put("org", org);
       			contextVolunteer.put("orgUser", orgUser);
       			contextVolunteer.put("project", project);
       			contextVolunteer.put("projectLink", urlService.getProjectUrl(project.getId()));
       			asyncEmailService.sendWithContext(Constants.C4SG_ADDRESS, user.getEmail(), orgUser.getEmail(), Constants.SUBJECT_APPLICAITON_VOLUNTEER, Constants.TEMPLATE_APPLICAITON_VOLUNTEER, contextVolunteer);
        	
        		System.out.println("Application email sent: Project=" + project.getId() + " ; ApplicantEmail=" + user.getEmail() + " ; OrgEmail=" + orgUser.getEmail());
        	
        	} else if (status.equals("C")) {
        		// send email to volunteer
       			Map<String, Object> contextVolunteer = new HashMap<String, Object>();
       			contextVolunteer.put("org", org);
       			contextVolunteer.put("orgUser", orgUser);
       			contextVolunteer.put("project", project);
       			contextVolunteer.put("projectLink", urlService.getProjectUrl(project.getId()));
       			asyncEmailService.sendWithContext(Constants.C4SG_ADDRESS, user.getEmail(), orgUser.getEmail(), Constants.SUBJECT_APPLICAITON_ACCEPT, Constants.TEMPLATE_APPLICAITON_ACCEPT, contextVolunteer);
        		System.out.println("Application email sent: Project=" + project.getId() + " ; ApplicantEmail=" + user.getEmail());
   
        	} else if (status.equals("D")) {
        		// send email to volunteer
       			Map<String, Object> contextVolunteer = new HashMap<String, Object>();
       			contextVolunteer.put("org", org);
       			contextVolunteer.put("orgUser", orgUser);
       			contextVolunteer.put("project", project);
       			contextVolunteer.put("projectLink", urlService.getProjectUrl(project.getId()));
       			asyncEmailService.sendWithContext(Constants.C4SG_ADDRESS, user.getEmail(), orgUser.getEmail(), Constants.SUBJECT_APPLICAITON_DECLINE, Constants.TEMPLATE_APPLICAITON_DECLINE, contextVolunteer);        	
        		System.out.println("Application email sent: Project=" + project.getId() + " ; ApplicantEmail=" + user.getEmail());

        	} else if (status.equals("B")) {
        		// do nothing
        	}
        }
    }
	        
	 /*private void isRecordExist(Integer userId, Integer projectId, String status) throws UserProjectException {

	    	List<UserProject> userProjects = userProjectDAO.findByUser_IdAndProject_IdAndStatus(userId, projectId, status);
	    	
	    	requireNonNull(userProjects, "Invalid operation");
	    	for(UserProject userProject : userProjects)
	    	{
	    		if(userProject.getStatus().equals(status))
	        	{
	        		throw new UserProjectException("Record already exist");
	        	}
	    	}    	
	    }*/
    
    private void isApplied(Integer userId, Integer projectId, String status) throws UserProjectException {

    	List<Application> applications = applicationDAO.findByUser_IdAndProject_IdAndStatus(userId, projectId, status);
    	
    	requireNonNull(applications, "Invalid operation");
    	for(Application application : applications)
    	{
    		if(application.getStatus().equals(status))
        	{
        		throw new UserProjectException("Record already exist");
        	}
    	}    	
    }
    
    private void isBookmarked(Integer userId, Integer projectId) throws UserProjectException {

    	List<Bookmark> bookmarks = bookmarkDAO.findByUser_IdAndProject_Id(userId, projectId);
    	
    	requireNonNull(bookmarks, "Invalid operation");
    	for(Bookmark bookmark : bookmarks)
    	{
    		if(bookmark.getUser().getId().equals(userId) && bookmark.getProject().getId().equals(projectId))
        	{
        		throw new UserProjectException("Record already exist");
        	}
    	}    	
    }
    
	
}
