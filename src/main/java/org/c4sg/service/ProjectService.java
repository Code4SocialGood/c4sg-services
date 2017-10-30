package org.c4sg.service;

import java.util.List;

import org.c4sg.dto.CreateProjectDTO;
import org.c4sg.dto.JobTitleDTO;
import org.c4sg.dto.ProjectDTO;
import org.c4sg.exception.ProjectServiceException;
import org.springframework.data.domain.Page;

public interface ProjectService {

	ProjectDTO findById(int id);

	ProjectDTO findByName(String name);

	List<ProjectDTO> findProjects();

	Page<ProjectDTO> search(String keyWord, List<Integer> jobTitles, List<Integer> skills, String status, String remote, Integer page, Integer size);

	List<ProjectDTO> findByOrganization(Integer orgId, String projectStatus);

	ProjectDTO createProject(CreateProjectDTO project);

	//List<ProjectDTO> findByUser(Integer userId, String userProjectStatus) throws ProjectServiceException;
	
	//List<ProjectDTO> getApplicationByUserAndStatus(Integer userId, String userProjectStatus);
	
	//List<ProjectDTO> getBookmarkByUser(Integer userId);
	
	//ProjectDTO saveUserProject(Integer userId, Integer projectId, String userProjectStatus) throws RuntimeException;
	
	//ProjectDTO saveApplication(Integer userId, Integer projectId, String status, String comment, String resumeFlag);
	
	
	
	//ProjectDTO updateApplication(Integer userId, Integer projectId, String status, String comment, String resumeFlag);
	
	//ProjectDTO updateBookmark(Integer userId, Integer projectId);
	
	ProjectDTO updateProject(ProjectDTO project);

	void deleteProject(int id);
	
	//void deleteApplication(int id);
	
	//void deleteBookmark(int id);
	
    void saveImage(Integer id, String imgUrl);

	List<JobTitleDTO> findJobTitles();
}
