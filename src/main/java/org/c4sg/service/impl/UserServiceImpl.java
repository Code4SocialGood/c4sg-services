package org.c4sg.service.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.c4sg.constant.Constants;
import org.c4sg.dao.ApplicationDAO;
import org.c4sg.dao.BookmarkDAO;
import org.c4sg.dao.OrganizationDAO;
import org.c4sg.dao.UserDAO;
import org.c4sg.dto.ApplicantDTO;
import org.c4sg.dto.CreateUserDTO;
import org.c4sg.dto.OrganizationDTO;
import org.c4sg.dto.UserDTO;
import org.c4sg.entity.Organization;
import org.c4sg.entity.User;
import org.c4sg.exception.NotFoundException;
import org.c4sg.exception.UserServiceException;
import org.c4sg.mapper.UserMapper;
import org.c4sg.service.AsyncEmailService;
import org.c4sg.service.GeocodeService;
import org.c4sg.service.OrganizationService;
import org.c4sg.service.UserService;
import org.c4sg.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDAO userDAO;
	
	@Autowired
	private OrganizationDAO organizationDAO;

	@Autowired
	private ApplicationDAO applicationDAO;
	
	@Autowired
	private BookmarkDAO bookmarkDAO;
	
	@Autowired
    private OrganizationService organizationService;

	@Autowired
	private UserMapper userMapper;
	
	@Autowired
	private GeocodeService geocodeService;
		
    @Autowired
    private AsyncEmailService asyncEmailService;
    
	@Override
	public List<UserDTO> findAll() {
		List<User> users = userDAO.findAllByOrderByIdDesc();
		return userMapper.getDtosFromEntities(users);
	}
	
	@Override
	public Page<UserDTO> findActiveVolunteers(Pageable pageable) {
		Page<User> users = userDAO.findActiveVolunteers(pageable);
		Page<UserDTO> userDTOS = users.map(p -> userMapper.getUserDtoFromEntity(p));
		return userDTOS;
	}

	@Override
	public List<UserDTO> findUsersToNotify() {
		List<User> users = userDAO.findByNotify();
		return userMapper.getDtosFromEntities(users);
	}
	
	@Override
	public UserDTO findById(int id) {
		return userMapper.getUserDtoFromEntity(userDAO.findById(id));
	}
	
	@Override
	public List<UserDTO> findByOrgId(int orgId) {
		return userMapper.getDtosFromEntities(userDAO.findByOrgId(orgId));
	}
	
	@Override
	public UserDTO findByEmail(String email) {
		return userMapper.getUserDtoFromEntity(userDAO.findByEmail(email));
	}
	
	@Override
	public UserDTO saveUser(UserDTO userDTO) {
		User user = userMapper.getUserEntityFromDto(userDTO);
				
		try {
			Map<String, BigDecimal> geoCode = geocodeService.getGeoCode(user.getState(), user.getCountry());
	        user.setLatitude(geoCode.get("lat"));
	        user.setLongitude(geoCode.get("lng"));
        }  catch (Exception e) {
        	throw new NotFoundException("Error getting geocode");
		}
		
		return userMapper.getUserDtoFromEntity(userDAO.save(user));
	}

    public void deleteUser(Integer id)
    {    	
        User user = userDAO.findById(id);
        
        // Verify either admin or email matches from authenticated user
        if (!JwtUtil.isAdmin() && !JwtUtil.match(user.getEmail())) {
        	throw new UserServiceException("Delete unauthorized");
        }
        
        user.setStatus(Constants.USER_STATUS_DELETED);
        user.setEmail(user.getEmail() + "-deleted");
        userDAO.save(user);        
        //userDAO.deleteUserProjects(id);
        applicationDAO.deleteByUser_Id(id); 
        bookmarkDAO.deleteByUser_Id(id);
        userDAO.deleteUserSkills(id);  
        List<OrganizationDTO> organizations = organizationService.findByUser(id);
        for (OrganizationDTO org:organizations) {
        	organizationService.deleteOrganization(org.getId());
        }
        
        // Sends notification to admin user. Delete user will be performed by admin user from Auth0 internally to reduce risk.  			
    	Map<String, Object> context = new HashMap<String, Object>();
    	context.put("user", user);         	
    	asyncEmailService.sendWithContext(Constants.C4SG_ADDRESS, Constants.C4SG_ADDRESS, "", Constants.SUBJECT_DELETE_USER, Constants.TEMPLATE_DELETE_USER, context);
    	System.out.println("Delete user email sent: User=" + id);

    }	

	@Override
	public Page<UserDTO> search(String keyWord, List<Integer> jobTitles, List<Integer> skills, String status, String role, String publishFlag, Integer page, Integer size) {

		Page<User> userPages = null;
		List<User> users = null;
		
		if (page == null) 
			page=0;
		
		if (size == null) {
			if(skills != null && jobTitles != null) {
				users = userDAO.findByKeywordAndJobAndSkill(keyWord, jobTitles, skills, status, role, publishFlag);
			} else if(skills != null) {
				users = userDAO.findByKeywordAndSkill(keyWord, skills, status, role, publishFlag);
			} else if(jobTitles != null) {
				users = userDAO.findByKeywordAndJob(keyWord, jobTitles, status, role, publishFlag);
	    	} else {
	    		users = userDAO.findByKeyword(keyWord, status, role, publishFlag);
	    	}
			userPages=new PageImpl<User>(users);
		} else {
			Pageable pageable = new PageRequest(page,size);
			if(skills != null && jobTitles != null) {
				userPages = userDAO.findByKeywordAndJobAndSkill(keyWord, jobTitles, skills, status, role, publishFlag, pageable);
			} else if(skills != null) {
				userPages = userDAO.findByKeywordAndSkill(keyWord, skills, status, role, publishFlag, pageable);
			} else if(jobTitles != null) {
				userPages = userDAO.findByKeywordAndJob(keyWord, jobTitles, status, role, publishFlag, pageable);
	    	} else {
	    		userPages = userDAO.findByKeyword(keyWord, status, role, publishFlag, pageable);
	    	}			
		}
		
		Page<UserDTO> userDTOS = userPages.map(p -> userMapper.getUserDtoFromEntity(p));
		return userDTOS;// mapUsersToUserDtos(users);
	}

	@Override
	public UserDTO createUser(CreateUserDTO createUserDTO) {
		
		User user = userMapper.getUserEntityFromCreateUserDto(createUserDTO);
		
		if ((user!= null) && !StringUtils.isEmpty(user.getCountry())) {
			try {
				Map<String, BigDecimal> geoCode = geocodeService.getGeoCode(user.getState(), user.getCountry());
				user.setLatitude(geoCode.get("lat"));
				user.setLongitude(geoCode.get("lng"));
			}  catch (Exception e) {
				// Don't throw exception, geocoding error won't present user from being created.
				System.out.println("Error getting geocode: " + e.toString());
			}
		}
        
		user.setStatus("N"); // Set user status to "N" for new user
        User userEntity = userDAO.save(user);
        
        // If the user is organization user:
        // 1. Creates an empty organization.
        // 2. Create a relationship between user and organization. The relationship is one to one.
        String role = createUserDTO.getRole();
        if ((role != null) && role.equals("O")) {
        	Organization organization = new Organization();
        	organization.setStatus("N");
        	Organization organizationEntity = organizationDAO.save(organization);        	
        	organizationService.saveUserOrganization(userEntity.getId(), organizationEntity.getId());  	        	
        }
        	        
        return userMapper.getUserDtoFromEntity(userEntity);
	}
	
	@Override
	public void saveAvatar(Integer id, String imgUrl) {
		userDAO.updateAvatar(imgUrl, id);
	}
}
