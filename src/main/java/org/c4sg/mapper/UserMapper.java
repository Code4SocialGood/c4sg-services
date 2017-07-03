package org.c4sg.mapper;

import org.c4sg.dto.CreateUserDTO;
import org.c4sg.dto.UserDTO;
import org.c4sg.entity.User;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Component;

import java.lang.reflect.Type;
import java.util.List;


@Component
public class UserMapper extends ModelMapper {
	
	UserMapper() {
	}
	
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
}