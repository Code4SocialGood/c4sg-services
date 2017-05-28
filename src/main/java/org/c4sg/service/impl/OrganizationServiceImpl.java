package org.c4sg.service.impl;


import org.c4sg.constant.Constants;
import org.c4sg.dao.OrganizationDAO;
import org.c4sg.dao.UserDAO;
import org.c4sg.dao.UserOrganizationDAO;
import org.c4sg.dto.CreateOrganizationDTO;
import org.c4sg.dto.OrganizationDTO;
import org.c4sg.dto.ProjectDTO;
import org.c4sg.entity.Organization;
import org.c4sg.entity.User;
import org.c4sg.entity.UserOrganization;
import org.c4sg.exception.UserOrganizationException;
import org.c4sg.mapper.OrganizationMapper;
import org.c4sg.service.OrganizationService;
import org.c4sg.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.nonNull;
import static java.util.Objects.requireNonNull;
import static org.c4sg.constant.Directory.LOGO_UPLOAD;
import static org.c4sg.constant.Format.IMAGE;

@Service
public class OrganizationServiceImpl implements OrganizationService {

    @Autowired
    private OrganizationDAO organizationDAO;

    @Autowired
    private OrganizationMapper organizationMapper;

	@Autowired
	private UserDAO userDAO;

	@Autowired
	private UserOrganizationDAO userOrganizationDAO;
	
	@Autowired
	private ProjectService projectService;

    public void save(OrganizationDTO organizationDTO) {
        Organization organization = organizationMapper.getOrganizationEntityFromDto(organizationDTO);
        organizationDAO.save(organization);
    }

    public List<OrganizationDTO> findOrganizations() {
        List<Organization> organizations = organizationDAO.findAllByOrderByIdDesc();
        List<OrganizationDTO> organizationDTOS = organizations.stream().map(o -> organizationMapper
                .getOrganizationDtoFromEntity(o)).collect(Collectors.toList());
        return organizationDTOS;
    }

    public OrganizationDTO findById(int id) {
        return organizationMapper.getOrganizationDtoFromEntity(organizationDAO.findOne(id));
    }

    public List<OrganizationDTO> findByKeyword(String keyWord) {
        List<Organization> organizations = organizationDAO.findByNameOrDescription(keyWord, keyWord);

        return organizations.stream()
                            .map(o -> organizationMapper.getOrganizationDtoFromEntity(o))
                            .collect(Collectors.toList());
    }
    
    public List<OrganizationDTO> findByCriteria(String keyWord, String country, boolean open) {
        List<Organization> organizations = organizationDAO.findByCriteria(keyWord, country, open);

        return organizations.stream()
                            .map(o -> organizationMapper.getOrganizationDtoFromEntity(o))
                            .collect(Collectors.toList());
    }
    
//    public OrganizationDTO createOrganization(OrganizationDTO organizationDTO) {
//        Organization organization = organizationDAO.save(organizationMapper.getOrganizationEntityFromDto(organizationDTO));
//        return organizationMapper.getOrganizationDtoFromEntity(organization);
//    }
    
    public OrganizationDTO createOrganization(CreateOrganizationDTO createOrganizationDTO) {
        Organization organization = organizationDAO.save(organizationMapper.getOrganEntityFromCreateOrganDto(createOrganizationDTO));
        return organizationMapper.getOrganizationDtoFromEntity(organization);
    }

    public OrganizationDTO updateOrganization(int id, OrganizationDTO organizationDTO) {
        Organization organization = organizationDAO.findOne(id);
        if (organization == null) {
        	System.out.println("Project does not exist.");
        } else {
            organization = organizationDAO.save(organizationMapper.getOrganizationEntityFromDto(organizationDTO));
        }

        return organizationMapper.getOrganizationDtoFromEntity(organization);
    }

    public void deleteOrganization(int id){
    	Organization organization = organizationDAO.findOne(id);
    	if(organization != null){
    		organization.setStatus(Constants.ORGANIZATION_STATUS_CLOSED);
    		organization.setLogoUrl(null);
    		organizationDAO.save(organization);
    		List<ProjectDTO> projects=projectService.findByOrganization(id);
    		for (ProjectDTO project:projects){
    			projectService.deleteProject(project.getId());
    		}
    		organizationDAO.deleteUserOrganizations(id);
    		//TODO: Local or Timezone?
    		//TODO: Format date
    		//organization.setDeleteTime(LocalDateTime.now().toString());
    		//organization.setDeleteBy(user.getUsername());
    	}
    }

    public String getLogoUploadPath(Integer organizationId) {
        return LOGO_UPLOAD.getValue() + File.separator + organizationId + IMAGE.getValue();
    }


    @Override
    public List<OrganizationDTO> findByUser(Integer userId) {
      User user = userDAO.findById(userId);
      requireNonNull(user, "Invalid User Id");
      List<UserOrganization> userOrganizations = userOrganizationDAO.findByUserId(userId);
      List<OrganizationDTO> organizationDtos = new ArrayList<OrganizationDTO>();
      for (UserOrganization userOrganization : userOrganizations) {
        organizationDtos.add(organizationMapper.getOrganizationDtoFromEntity(userOrganization));
      }
      return organizationDtos;
    }
    
    @Override
    public OrganizationDTO saveUserOrganization(Integer userId, Integer organizationId) throws UserOrganizationException {
        User user = userDAO.findById(userId);
        requireNonNull(user, "Invalid User Id");
        Organization organization = organizationDAO.findOne(organizationId);
        requireNonNull(organization, "Invalid organization Id");
        UserOrganization userOrganization = userOrganizationDAO.findByUser_IdAndOrganization_Id(userId, organizationId);
        if (nonNull(userOrganization)) {
            throw new UserOrganizationException("The user organization relationship already exists.");
        } else {
        	userOrganization = new UserOrganization();
        	userOrganization.setUser(user);
        	userOrganization.setOrganization(organization);
        	userOrganizationDAO.save(userOrganization);
        }
        
        return organizationMapper.getOrganizationDtoFromEntity(organization);
    }
}
