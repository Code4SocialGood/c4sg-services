package org.c4sg.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.c4sg.entity.Project;
import org.c4sg.entity.UserProject;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface UserProjectDAO extends CrudRepository<UserProject, Long>{
    
	
	UserProject findByUser_IdAndProject_Id(Integer userId, Integer projectId);
    List<UserProject> findByUserId(Integer userId);
    List<UserProject> findByUser_IdAndProject_IdAndStatus(Integer userId, Integer projectId, String status);   
    
  
    
    String UPDATE_STATUS = "UPDATE UserProject up SET up.status = :userProjectStatus WHERE up.project.id = :projId";
    
    
    @Transactional
    @Modifying
	@Query(UPDATE_STATUS)
    Integer updateStatus(@Param("projId") Integer projId, @Param("userProjectStatus") String userProjectStatus);
}
