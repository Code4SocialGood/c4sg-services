package org.c4sg.service;

import java.util.List;

import org.c4sg.dto.ApplicantDTO;
import org.c4sg.dto.ApplicationDTO;
import org.c4sg.dto.ApplicationProjectDTO;
import org.c4sg.dto.ProjectDTO;


public interface ApplicationService {
	
	List<ProjectDTO> getApplicationsByUser(Integer userId, String status);
	List<ApplicantDTO> getApplicants(Integer projectId);
	List<ApplicationProjectDTO> getApplicationsByOrgAndByApplicant(Integer userId, Integer nonProfitUserId, String status);
	
	ApplicationDTO createApplication(ApplicationDTO application);
	ApplicationDTO updateApplication(ApplicationDTO application);
	Long deleteApplication(Integer id);

}
