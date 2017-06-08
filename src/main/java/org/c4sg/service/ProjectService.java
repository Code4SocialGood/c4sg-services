package org.c4sg.service;

import java.util.List;

import org.c4sg.dto.CreateProjectDTO;
import org.c4sg.dto.ProjectDTO;
import org.c4sg.exception.ProjectServiceException;

public interface ProjectService {

	ProjectDTO findById(int id);

	ProjectDTO findByName(String name);

	List<ProjectDTO> findProjects();

	List<ProjectDTO> findByKeyword(String keyWord, List<Integer> skills, String status, String remote);

	List<ProjectDTO> findByUser(Integer userId, String userProjectStatus) throws ProjectServiceException;

	List<ProjectDTO> findByOrganization(Integer orgId, String projectStatus);

	ProjectDTO createProject(CreateProjectDTO project);

	ProjectDTO saveUserProject(Integer userId, Integer projectId, String userProjectStatus) throws RuntimeException;

	ProjectDTO updateProject(ProjectDTO project);

	void deleteProject(int id);

	//void apply(UserDTO user, ProjectDTO project) throws IOException, EmailException;

	String getImageUploadPath(Integer projectId);

}
