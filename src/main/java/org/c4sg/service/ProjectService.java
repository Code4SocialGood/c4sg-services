package org.c4sg.service;

import java.io.IOException;
import java.util.List;

import org.apache.commons.mail.EmailException;
import org.c4sg.dto.CreateProjectDTO;
import org.c4sg.dto.ProjectDTO;
import org.c4sg.entity.Project;
import org.c4sg.entity.User;
import org.c4sg.exception.ProjectServiceException;

public interface ProjectService {

	Project findById(int id);

	Project findByName(String name);

	List<ProjectDTO> findProjects();

	List<ProjectDTO> findByKeyword(String name, String keyWord);

	List<ProjectDTO> findByUser(Integer userId, String userProjectStatus) throws ProjectServiceException;

	List<Project> findByOrganization(Integer orgId);

	Project createProject(Project project);

	Project createProject(CreateProjectDTO project);

	ProjectDTO saveUserProject(Integer userId, Integer projectId) throws RuntimeException;

	Project updateProject(Project project);

	void deleteProject(int id);

	void apply(User user, Project project) throws IOException, EmailException;

	String getImageUploadPath(Integer projectId);

	void saveUserProjectBookmark(Integer userId, Integer projectId);
}
