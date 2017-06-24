package org.c4sg.service;

import java.util.List;

import org.c4sg.dto.CreateProjectDTO;
import org.c4sg.dto.ProjectDTO;
import org.c4sg.exception.ProjectServiceException;
import org.springframework.data.domain.Page;

public interface ProjectService {

	ProjectDTO findById(int id);

	ProjectDTO findByName(String name);

	List<ProjectDTO> findProjects();

	Page<ProjectDTO> findByKeyword(String keyWord, List<Integer> skills, String status, String remote,int page, int size);

	List<ProjectDTO> findByUser(Integer userId, String userProjectStatus) throws ProjectServiceException;

	List<ProjectDTO> findByOrganization(Integer orgId, String projectStatus);

	ProjectDTO createProject(CreateProjectDTO project);

	ProjectDTO saveUserProject(Integer userId, Integer projectId, String userProjectStatus) throws RuntimeException;

	ProjectDTO updateProject(ProjectDTO project);

	void deleteProject(int id);
	
    void saveImage(Integer id, String imgUrl);

}
