package org.c4sg.service;

import java.util.List;

import org.c4sg.dto.ApplicantDTO;
import org.c4sg.dto.CreateUserDTO;
import org.c4sg.dto.UserDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {
	
    List<UserDTO> findAll();
    
	List<UserDTO> findUsersToNotify();
	
    Page<UserDTO> findActiveVolunteers(Pageable pageable);
    
    UserDTO findById(int id);

    UserDTO findByEmail(String email);

    UserDTO saveUser(UserDTO userDTO);

    void deleteUser(Integer id);

    Page<UserDTO> search(String keyWord, List<Integer> jobTitles, List<Integer> skills, String status, String role, String publishFlag,Integer page, Integer size);
           
    UserDTO createUser(CreateUserDTO createUserDTO);
    
    void saveAvatar(Integer id, String imgUrl);

	List<UserDTO> findByOrgId(int orgId);
}