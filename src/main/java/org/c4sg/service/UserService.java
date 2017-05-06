
package org.c4sg.service;

import org.c4sg.dto.UserDTO;
import org.c4sg.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserService {
	
    List<UserDTO> findAll();

    Page<UserDTO> findActiveVolunteers(Pageable pageable);
    
    UserDTO findById(int id);

    UserDTO findByEmail(String email);

    UserDTO saveUser(UserDTO userDTO);

    void deleteUser(Integer id);

    List<UserDTO> search(String keyWord, List<Integer> skills);

    List<UserDTO> getApplicants(Integer projectId);
    
    String getAvatarUploadPath(Integer userId);
    
    String getResumeUploadPath(Integer userId);
}
