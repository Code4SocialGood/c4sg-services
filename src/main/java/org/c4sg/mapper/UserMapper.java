package org.c4sg.mapper;

import org.c4sg.dto.ApplicantDTO;
import org.c4sg.dto.CreateUserDTO;
import org.c4sg.dto.UserDTO;
import org.c4sg.entity.User;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Component;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;


@Component
public class UserMapper extends ModelMapper {
	
	private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss.SSS";
			
	public UserDTO getUserDtoFromEntity(User user){
		if (user == null)
			return null;
		
		UserDTO userDTO = map(user, UserDTO.class);
		return userDTO;
	}
	
	public User getUserEntityFromDto(UserDTO userDTO){
		User user = map(userDTO, User.class);
		return user;
	}

	public List<UserDTO> getDtosFromEntities(List<User> projects){
		Type listTypeDTO = new TypeToken<List<UserDTO>>() {}.getType();
		return map(projects, listTypeDTO);
	}
	
	public User getUserEntityFromCreateUserDto(CreateUserDTO createUserDTO) {
		return map(createUserDTO, User.class);
	}
	
	public List<ApplicantDTO> getApplicantDTOs(List<Object[]> applicants) {
		
		List<ApplicantDTO> applicantList = new ArrayList<>();
		Iterator<Object[]> iter = applicants.iterator();
		while (iter.hasNext()) {
			Object[] o = iter.next();
			ApplicantDTO applicant = new ApplicantDTO();
			applicant.setUserId((Integer)o[0]);
			applicant.setProjectId((Integer)o[1]);
			applicant.setFirstName((String)o[2]);
			applicant.setLastName((String)o[3]);
			applicant.setTitle((String)o[4]);
			applicant.setApplicationStatus(String.valueOf(o[5]));
			if (o[6] != null) {
				applicant.setAppliedTime((Date)o[6]);				
			} 	
			if (o[7] != null) {
				applicant.setAcceptedTime((Date) o[7]);				
			} 	
			if (o[8] != null) {
				applicant.setDeclinedTime((Date) o[8]);				
			} 	
			
			applicantList.add(applicant);
		}
		return applicantList;
	}
}