package org.c4sg.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.c4sg.entity.Application;
import org.c4sg.entity.UserProject;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface ApplicationDAO extends CrudRepository<Application, Long> {
	
	String DELETE_USER_PROJECTS = "DELETE FROM UserProject up WHERE up.user.id=:userId";
	
	Application findByUser_IdAndProject_IdAndStatus(Integer userId, Integer projectId, String status);
	Application findByUser_IdAndProject_Id(Integer userId, Integer projectId);
	List<Application> findByUser_Id(Integer userId);
	List<Application> findByUser_IdAndStatus(Integer userId, String status);
	List<Application> findByProject_Id(Integer projectId);
	List<Application> findByProject_IdAndStatus(Integer projectId, String status);
		
	@Transactional
	Long deleteById(Integer id);	
	@Transactional
	Long deleteByUser_Id(Integer userid);
	@Transactional
	Long deleteByProject_id(Integer projectid);
	

}
