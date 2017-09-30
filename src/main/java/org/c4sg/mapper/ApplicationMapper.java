package org.c4sg.mapper;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.c4sg.dto.ApplicationDTO;
import org.c4sg.dto.ProjectDTO;
import org.c4sg.entity.Application;
import org.c4sg.entity.Project;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class ApplicationMapper extends ModelMapper {
	
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

}
