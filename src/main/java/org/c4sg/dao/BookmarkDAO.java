package org.c4sg.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.c4sg.entity.Application;
import org.c4sg.entity.Bookmark;
import org.springframework.data.repository.CrudRepository;

public interface BookmarkDAO extends CrudRepository<Bookmark, Long> {

	Bookmark findByUser_IdAndProject_Id(Integer userId, Integer projectId);
	List<Bookmark> findByUser_Id(Integer userId);	
	List<Bookmark> findByProject_Id(Integer projectId);	
	
	@Transactional
	Long deleteById(Integer id);	
	@Transactional
	Long deleteByUser_Id(Integer userid);
	@Transactional
	Long deleteByProject_id(Integer projectid);
}
