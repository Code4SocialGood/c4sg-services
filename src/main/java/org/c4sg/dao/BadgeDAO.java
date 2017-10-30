package org.c4sg.dao;

import java.util.List;

import org.c4sg.entity.Badge;
import org.c4sg.entity.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface BadgeDAO extends CrudRepository<Badge, Long> {
	String FIND_HEROES = "select u, count(*) as badgeCount from Badge b inner join b.user u group by u order by badgeCount desc, u.firstName asc, u.lastName asc";
	@Query(FIND_HEROES) 
    List<Object[]> findHeroes(); 
    List<Badge> findByUser_IdOrderByCreatedTimeAsc(Integer userId);
    Badge findByUser_IdAndProject_Id(Integer userId, Integer projectId);    
}
