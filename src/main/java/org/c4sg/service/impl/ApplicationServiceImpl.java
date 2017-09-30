package org.c4sg.service.impl;

import static java.util.Objects.requireNonNull;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.c4sg.constant.Constants;
import org.c4sg.dao.ApplicationDAO;
import org.c4sg.dao.ProjectDAO;
import org.c4sg.dao.UserDAO;
import org.c4sg.dto.ApplicationDTO;
import org.c4sg.entity.Application;
import org.c4sg.entity.Organization;
import org.c4sg.entity.Project;
import org.c4sg.entity.User;
import org.c4sg.exception.BadRequestException;
import org.c4sg.exception.UserProjectException;
import org.c4sg.mapper.ApplicationMapper;
import org.c4sg.mapper.converter.BooleanToStringConverter;
import org.c4sg.service.ApplicationService;
import org.c4sg.service.AsyncEmailService;
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
	private ApplicationMapper applicationMapper;
	
	@Autowired
	private BooleanToStringConverter booleanToStringConverter;
	
	public ApplicationDTO getApplicationByProjectandByUser(Integer userId, Integer projectId){
		
		Application application = applicationDAO.findByUser_IdAndProject_Id(userId, projectId);
		return applicationMapper.getApplicationDtoFromEntity(application);
		
	}
	public ApplicationDTO getApplicationsByProjectAndByUser(Integer userId, Integer projectId, String status){
		
		Application application = applicationDAO.findByUser_IdAndProject_IdAndStatus(userId, projectId, status);
		return applicationMapper.getApplicationDtoFromEntity(application);
	}
	public List<ApplicationDTO> getApplicationsByUser(Integer userId){
		
		List<Application> applications = applicationDAO.findByUser_Id(userId);
		return applicationMapper.getApplicationDtosFromEntities(applications);
		
	}
	public List<ApplicationDTO> getApplicationsByUser(Integer userId, String status){
		
		List<Application> applications = applicationDAO.findByUser_IdAndStatus(userId, status);
		return applicationMapper.getApplicationDtosFromEntities(applications);
		
	}
	public List<ApplicationDTO> getApplicationsByProject(Integer projectId){
		
		List<Application> applications = applicationDAO.findByProject_Id(projectId);
		return applicationMapper.getApplicationDtosFromEntities(applications);
		
	}
	public List<ApplicationDTO> getApplicationsByProject(Integer projectId, String status){
		
		List<Application> applications = applicationDAO.findByProject_IdAndStatus(projectId, status);
		return applicationMapper.getApplicationDtosFromEntities(applications);
		
	}
	
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
    	application.setAppliedTime(new Timestamp(Calendar.getInstance().getTime().getTime()));
        applicationDAO.save(application);
	    
        asyncEmailService.sendEmail(user, project, applicationDto.getStatus());
        
        return applicationMapper.getApplicationDtoFromEntity(application);
		
	}
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
        applicationDAO.save(application);
	    
        User user = userDAO.findById(applicationDto.getUserId());
		Project project = projectDAO.findById(applicationDto.getProjectId());
        asyncEmailService.sendEmail(user, project, applicationDto.getStatus());
        
        return applicationMapper.getApplicationDtoFromEntity(application);
	}
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
        		
        	} else if (status.equals("C") && java.util.Objects.nonNull(application.getAcceptedTime())){
        		
        		throw new UserProjectException("Already accepted for the project.");	
        		
        	} else if (status.equals("D") && java.util.Objects.nonNull(application.getDeclinedTime())){
        		
        		throw new UserProjectException("Already declined for the project.");
        	}
    	   	
    }
	
	

}
