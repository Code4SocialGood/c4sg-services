package org.c4sg.service;

import java.util.List;

import org.c4sg.dto.ApplicationDTO;


public interface ApplicationService {
	
	ApplicationDTO getApplicationByProjectandByUser(Integer userId, Integer projectId);
	ApplicationDTO getApplicationsByProjectAndByUser(Integer userId, Integer projectId, String status);
	List<ApplicationDTO> getApplicationsByUser(Integer userId);	
	List<ApplicationDTO> getApplicationsByUser(Integer userId, String status);
	List<ApplicationDTO> getApplicationsByProject(Integer projectId);
	List<ApplicationDTO> getApplicationsByProject(Integer projectId, String status);
	
	ApplicationDTO createApplication(ApplicationDTO application);
	ApplicationDTO updateApplication(ApplicationDTO application);
	Long deleteApplication(Integer id);

}
