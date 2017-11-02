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
import org.c4sg.service.AsyncEmailService;
import org.c4sg.service.GeocodeService;
import org.c4sg.service.OrganizationService;
import org.c4sg.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.Objects.nonNull;
import static java.util.Objects.requireNonNull;

import java.math.BigDecimal;

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
	
    @Autowired
    private AsyncEmailService asyncEmailService;
    
    @Autowired
	private GeocodeService geocodeService;

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
    
    public Page<OrganizationDTO> findByCriteria(String keyWord, List<String> countries, Boolean open, String status, List<String> categories, Integer page, Integer size) {
    	Page<Organization> organizationPages=null;
    	List<Organization> organizations=null;
    	if (page==null) page=0;
    	if (size==null){
	    	if(countries != null && !countries.isEmpty()){
	    		if(open != null){
	    			organizations = organizationDAO.findByCriteriaAndCountriesAndOpen(keyWord, countries, open, status, categories);
	    		}
	    		else{    			
	    			organizations = organizationDAO.findByCriteriaAndCountries(keyWord, countries, open, status, categories);
	    		}	
	    		
	        }
	    	else{
	    		if(open != null){
	    			organizations = organizationDAO.findByCriteriaAndOpen(keyWord, open, status, categories);
	    		}
	    		else{    			
	    			organizations = organizationDAO.findByCriteria(keyWord, open, status, categories);
	    		}    		
	    	}
	    	organizationPages=new PageImpl<Organization>(organizations);
    	} else {
			Pageable pageable=new PageRequest(page,size);    	    	
	    	if(countries != null && !countries.isEmpty()){
	    		if(open != null){
	    			organizationPages = organizationDAO.findByCriteriaAndCountriesAndOpen(keyWord, countries, open, status, categories,pageable);
	    		}
	    		else{    			
	    			organizationPages = organizationDAO.findByCriteriaAndCountries(keyWord, countries, open, status, categories,pageable);
	    		}	
	    		
	        }
	    	else{
	    		if(open != null){
	    			organizationPages = organizationDAO.findByCriteriaAndOpen(keyWord, open, status, categories,pageable);
	    		}
	    		else{    			
	    			organizationPages = organizationDAO.findByCriteria(keyWord, open, status, categories,pageable);
	    		}    		
	    	}    		
    	}
    	return organizationPages.map(o -> organizationMapper.getOrganizationDtoFromEntity(o));    	
    }
      
    public OrganizationDTO createOrganization(CreateOrganizationDTO createOrganizationDTO) {
        Organization organization = organizationDAO.save(organizationMapper.getOrganEntityFromCreateOrganDto(createOrganizationDTO));
        return organizationMapper.getOrganizationDtoFromEntity(organization);
    }

    public OrganizationDTO updateOrganization(int id, OrganizationDTO organizationDTO) {
    	
        Organization organization = organizationDAO.findOne(id);

        if (organization == null) {
        	System.out.println("Organization does not exist.");
        } else {
        	organization = organizationMapper.getOrganizationEntityFromDto(organizationDTO);
            try {
    			Map<String, BigDecimal> geoCode = geocodeService.getGeoCode(organization.getState(), organization.getCountry());
    			organization.setLatitude(geoCode.get("lat"));
    			organization.setLongitude(geoCode.get("lng"));
            }  catch (Exception e) {
            	//throw new NotFoundException("Error getting geocode");
            	System.out.println("Error getting geocode: " + e.toString());
    		}
            organizationDAO.save(organization);
            String newStatus = organization.getStatus();
            
            // Notify admin users of new organization, or update for a declined organization
            if (newStatus.equals(Constants.ORGANIZATION_STATUS_PENDIONG_REVIEW) || newStatus.equals(Constants.ORGANIZATION_STATUS_DECLINED)) {
            	
            	String toAddress = null;
            	List<User> users = userDAO.findByKeyword(null, "A", "A", null);
            	if (users != null && !users.isEmpty()) {
            		User adminUser = users.get(0);
            		toAddress = adminUser.getEmail();
            	}	
            			
            	Map<String, Object> context = new HashMap<String, Object>();
            	context.put("organization", organization);         	
            	asyncEmailService.sendWithContext(Constants.C4SG_ADDRESS, toAddress, "", Constants.SUBJECT_NEW_ORGANIZATION_REVIEW, Constants.TEMPLATE_NEW_ORGANIZATION_REVIEW, context);
            	System.out.println("New organization email sent: Organization=" + organization.getId() + " ; Email=" + toAddress);
            }
        }
        
        return organizationMapper.getOrganizationDtoFromEntity(organization);
    }

    public void deleteOrganization(int id){
    	Organization organization = organizationDAO.findOne(id);
    	if(organization != null){
    		organization.setStatus(Constants.ORGANIZATION_STATUS_DELETED);
    		// TODO Delete logo from S3 by frontend
    		organizationDAO.save(organization);
    		List<ProjectDTO> projects=projectService.findByOrganization(id, null);
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
    
	@Override
	public void saveLogo(Integer id, String imgUrl) {
		organizationDAO.updateLogo(imgUrl, id);
	}
	
	@Override
	public void approveOrDecline(Integer id, String status) {
		organizationDAO.approveOrDecline(id, status);
		
        // Notify organization user of approval or deny
       	String toAddress = null;
    	List<User> users = userDAO.findByOrgId(id);
    	if (users != null && !users.isEmpty()) {
    		User orgUser = users.get(0);
    		toAddress = orgUser.getEmail();
    	}	
        			
       	Map<String, Object> context = new HashMap<String, Object>();     	
       	if (status.equals(Constants.ORGANIZATION_STATUS_ACTIVE))
       		asyncEmailService.sendWithContext(Constants.C4SG_ADDRESS, toAddress, "", Constants.SUBJECT_NEW_ORGANIZATION_APPROVE, Constants.TEMPLATE_NEW_ORGANIZATION_APPROVE, context);
       	else if (status.equals(Constants.ORGANIZATION_STATUS_DECLINED))
       		asyncEmailService.sendWithContext(Constants.C4SG_ADDRESS, toAddress, "", Constants.SUBJECT_NEW_ORGANIZATION_DECLINE, Constants.TEMPLATE_NEW_ORGANIZATION_DECLINE, context);
       	System.out.println("Organization approval/decline email sent: Organization=" + id + " ; Email=" + toAddress);
	}

	@Override
	public int countByCountry() {
		return organizationDAO.countByCountry();
	}
}
