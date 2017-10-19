package org.c4sg.mapper;

import org.c4sg.dao.OrganizationDAO;
import org.c4sg.dto.CreateProjectDTO;
import org.c4sg.dto.JobTitleDTO;
import org.c4sg.dto.ProjectDTO;
import org.c4sg.entity.Application;
import org.c4sg.entity.Bookmark;
import org.c4sg.entity.JobTitle;
import org.c4sg.entity.Project;
import org.c4sg.entity.UserProject;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Component
public class ProjectMapper extends ModelMapper{
	@Autowired
	private OrganizationDAO organizationDAO;
	
	public ProjectDTO getProjectDtoFromEntity(Project project){
		getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		ProjectDTO projectDTO = map(project, ProjectDTO.class);
		if (project.getOrganization() != null) {
			projectDTO.setOrganizationId(project.getOrganization().getId().toString());
			projectDTO.setOrganizationName(project.getOrganization().getName());
		} 
		
		return projectDTO;
	}
	
	public ProjectDTO getProjectDtoFromEntity(UserProject userProject){
		Type projectTypeDTO = new TypeToken<ProjectDTO>() {}.getType();
		ProjectDTO projectDTO = map(userProject.getProject(), projectTypeDTO);
		return projectDTO;
	}		

	public List<ProjectDTO> getDtosFromEntities(List<Project> projects){
		List<ProjectDTO> projectList = new ArrayList<ProjectDTO>();
		Iterator<Project> projectIter = projects.iterator();
		while (projectIter.hasNext()) {
			Project project = projectIter.next();
			projectList.add(getProjectDtoFromEntity(project));
		}
		return projectList;
	}

	public Project getProjectEntityFromDto(ProjectDTO projectDTO){
		Project project = map(projectDTO, Project.class);
		return project;
	}
	
	public Project getProjectEntityFromCreateProjectDto(CreateProjectDTO createProjectDTO){
		getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		Project project = map(createProjectDTO, Project.class);
		project.setOrganization(organizationDAO.findOne(createProjectDTO.getOrganizationId()));
		return project;
	}	
	
	public List<JobTitleDTO> getJobTitleDtosFromEntities(List<JobTitle> jobTitles){
        Type listTypeDTO = new TypeToken<List<JobTitleDTO>>() {}.getType();
		return map(jobTitles, listTypeDTO);
	}
}
