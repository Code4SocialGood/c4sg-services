package org.c4sg.dao;

import java.util.List;

import org.c4sg.entity.Application;
import org.c4sg.entity.UserProject;
import org.springframework.data.repository.CrudRepository;

public interface ApplicationDAO extends CrudRepository<Application, Long> {
	
	List<Application> findByUser_IdAndProject_IdAndStatus(Integer userId, Integer projectId, String status);
	Application findByUser_IdAndProject_Id(Integer userId, Integer projectId);
	List<Application> findByUser_Id(Integer userId);
	List<Application> findByUser_IdAndStatus(Integer userId, String status);
	List<Application> findByProject_Id(Integer projectId);
	List<Application> findByProject_IdAndStatus(Integer projectId, String status);
	Long deleteById(Integer id);

}
