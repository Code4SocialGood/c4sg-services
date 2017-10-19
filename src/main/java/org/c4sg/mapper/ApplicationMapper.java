package org.c4sg.mapper;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.c4sg.dto.ApplicantDTO;
import org.c4sg.dto.ApplicationDTO;
import org.c4sg.dto.ApplicationProjectDTO;
import org.c4sg.dto.ProjectDTO;
import org.c4sg.entity.Application;
import org.c4sg.entity.Project;
import org.c4sg.mapper.converter.BooleanToStringConverter;
import org.c4sg.mapper.converter.StringToBooleanConverter;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ApplicationMapper extends ModelMapper {
	
	@Autowired
	private StringToBooleanConverter stringToBooleanConverter;
	
	public ApplicationDTO getApplicationDtoFromEntity(Application application){
		ApplicationDTO applicationDTO = map(application, ApplicationDTO.class);
		return applicationDTO;
	}
	
	public Application getApplicationEntityFromDto(ApplicationDTO applicationDTO){
		Application application = map(applicationDTO, Application.class);
		return application;
	}
	
	public List<ApplicationDTO> getApplicationDtosFromEntities(List<Application> applications){
		List<ApplicationDTO> applicationList = new ArrayList<ApplicationDTO>();
		Iterator<Application> applicationIter = applications.iterator();
		while (applicationIter.hasNext()) {
			Application application = applicationIter.next();
			applicationList.add(getApplicationDtoFromEntity(application));
		}
		return applicationList;
	}
	
	public List<ApplicantDTO> getApplicantDtosFromEntities(List<Application> applications){
		
		List<ApplicantDTO> applicantList = new ArrayList<ApplicantDTO>();
		ApplicantDTO applicant;
		
		for(Application application: applications){
			applicant = new ApplicantDTO();
			
			applicant.setUserId(application.getUser().getId());
			applicant.setProjectId(application.getProject().getId());
			applicant.setFirstName(application.getUser().getFirstName());
			applicant.setLastName(application.getUser().getLastName());
			applicant.setTitle(application.getUser().getTitle());
			applicant.setApplicationStatus(application.getStatus());
			applicant.setComment(application.getComment());
			applicant.setResumeFlag(stringToBooleanConverter.convert(application.getResumeFlag()));
			applicant.setAppliedTime(application.getAppliedTime());
			applicant.setAcceptedTime(application.getAcceptedTime());
			applicant.setDeclinedTime(application.getDeclinedTime());	
			
			applicantList.add(applicant);
		}		
		
		return applicantList;
		
	}
	
	public List<ApplicationProjectDTO> getApplicationProjectDtosFromEntities(List<Application> applications){
		
		List<ApplicationProjectDTO> applicationList = new ArrayList<ApplicationProjectDTO>();
		ApplicationProjectDTO applicationProject;
		
		for(Application application: applications){
			applicationProject = new ApplicationProjectDTO();
			
			applicationProject.setUserId(application.getUser().getId());
			applicationProject.setProjectId(application.getProject().getId());
			applicationProject.setProjectName(application.getProject().getName());			
			applicationProject.setApplicationStatus(application.getStatus());
			applicationProject.setComment(application.getComment());
			applicationProject.setResumeFlag(stringToBooleanConverter.convert(application.getResumeFlag()));
			applicationProject.setAppliedTime(application.getAppliedTime());
			applicationProject.setAcceptedTime(application.getAcceptedTime());
			applicationProject.setDeclinedTime(application.getDeclinedTime());	
			
			applicationList.add(applicationProject);
		}		
		
		return applicationList;
		
	}

	public ProjectDTO getProjectDtoFromEntity(Application application){
		Type projectTypeDTO = new TypeToken<ProjectDTO>() {}.getType();
		ProjectDTO projectDTO = map(application.getProject(), projectTypeDTO);
		return projectDTO;
	}
	
	public List<ProjectDTO> getProjectDtosFromApplicationEntities(List<Application> applications){
		List<ProjectDTO> projectList = new ArrayList<ProjectDTO>();
		Iterator<Application> applicationIter = applications.iterator();
		while (applicationIter.hasNext()) {
			Application application = applicationIter.next();
			projectList.add(getProjectDtoFromEntity(application));
		}
		return projectList;
	}	
	
}
