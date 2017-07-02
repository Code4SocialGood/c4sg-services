package org.c4sg.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.c4sg.entity.UserProject;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface UserProjectDAO extends CrudRepository<UserProject, Long>{
    
	
	UserProject findByUser_IdAndProject_Id(Integer userId, Integer projectId);
    List<UserProject> findByUserId(Integer userId);
    List<UserProject> findByUser_IdAndProject_IdAndStatus(Integer userId, Integer projectId, String status);   
        
    String DELETE_BY_PROJECT_STATUS = "DELETE UserProject up WHERE up.project.id = :projId and up.status = :userProjectStatus" ;
        
    @Transactional
    @Modifying
	@Query(DELETE_BY_PROJECT_STATUS)
    Integer deleteByProjectStatus(@Param("projId") Integer projId, @Param("userProjectStatus") String userProjectStatus);
}
