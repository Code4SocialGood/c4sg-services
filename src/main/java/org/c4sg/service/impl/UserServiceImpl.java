package org.c4sg.service.impl;
import org.c4sg.constant.Constants;
import org.c4sg.dao.UserDAO;
import org.c4sg.dto.CreateUserDTO;
import org.c4sg.dto.OrganizationDTO;
import org.c4sg.dto.UserDTO;
import org.c4sg.entity.User;
import org.c4sg.exception.NotFoundException;
import org.c4sg.mapper.UserMapper;
import org.c4sg.service.OrganizationService;
import org.c4sg.service.UserService;
import org.c4sg.util.GeoCodeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDAO userDAO;

	@Autowired
    private OrganizationService organizationService;

	@Autowired
	private UserMapper userMapper;

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
		GeoCodeUtil geoCodeUtil = new GeoCodeUtil(user.getState(), user.getCountry()); //calculate lat and long
		try {
        	Map<String, BigDecimal> geoCode = geoCodeUtil.getGeoCode();
            user.setLatitude(geoCode.get("lat"));
            user.setLongitude(geoCode.get("lng"));
        }  catch (Exception e) {
        	throw new NotFoundException("Error getting geocode");
		}
		return userMapper.getUserDtoFromEntity(userDAO.save(user));
	}

    public void deleteUser(Integer id) {
        User user = userDAO.findById(id);
        user.setStatus(Constants.USER_STATUS_DELETED);
        user.setEmail(user.getEmail() + "-deleted");
        // TODO delete avatar from S3 by frontend
        userDAO.save(user);
        userDAO.deleteUserProjects(id);
        userDAO.deleteUserSkills(id);  
        List<OrganizationDTO> organizations = organizationService.findByUser(id);
        for (OrganizationDTO org:organizations){
        	organizationService.deleteOrganization(org.getId());
        }
    }
		
	@Override
	public List<UserDTO> getApplicants(Integer projectId) {
		List<User> users = userDAO.findByUserProjectId(projectId, Constants.USER_PROJECT_STATUS_APPLIED);
		return userMapper.getDtosFromEntities(users);
	}

	@Override
	public Page<UserDTO> search(String keyWord, List<Integer> skills, String status, String role, String publishFlag, Integer page, Integer size) {

		Page<User> userPages = null;
		List<User> users=null;
		if (page==null) page=0;
		if (size==null){
			if(skills != null)
	    	{
				users = userDAO.findByKeywordAndSkill(keyWord, skills, status, role, publishFlag);
	    	}
	    	else{
	    		users = userDAO.findByKeyword(keyWord, status, role, publishFlag);
	    	}
			userPages=new PageImpl<User>(users);
		}else{
			Pageable pageable=new PageRequest(page,size);
			if(skills != null)
	    	{
				userPages = userDAO.findByKeywordAndSkill(keyWord, skills, status, role, publishFlag,pageable);
	    	}
	    	else{
	    		userPages = userDAO.findByKeyword(keyWord, status, role, publishFlag,pageable);
	    	}			
		}
		Page<UserDTO> userDTOS = userPages.map(p -> userMapper.getUserDtoFromEntity(p));
		return userDTOS;// mapUsersToUserDtos(users);
	}

	@Override
	public UserDTO createUser(CreateUserDTO createUserDTO) {
		
		GeoCodeUtil geoCodeUtil = new GeoCodeUtil(createUserDTO.getState(), createUserDTO.getCountry()); //calculate lat and long
		User user = userMapper.getUserEntityFromCreateUserDto(createUserDTO);
		try {
        	Map<String, BigDecimal> geoCode = geoCodeUtil.getGeoCode();
            user.setLatitude(geoCode.get("lat"));
            user.setLongitude(geoCode.get("lng"));
        }  catch (Exception e) {
        	throw new NotFoundException("Error getting geocode");
		}
        
        User localUser = userDAO.save(user);
        return userMapper.getUserDtoFromEntity(localUser);
	}
	
	@Override
	public void saveAvatar(Integer id, String imgUrl) {
		userDAO.updateAvatar(imgUrl, id);
	}
}
