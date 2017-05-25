package org.c4sg.dao;

import org.c4sg.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;

import java.util.List;

import javax.transaction.Transactional;

@Component
public interface UserDAO extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {
	
    String FIND_ACTIVE_VOLUNTEERS = 
    		"SELECT u FROM User u " +
            "WHERE u.status = 'A' and u.role = 'V' and u.publishFlag = 'Y' " + 
    		"ORDER BY u.createdTime DESC";
    
    String FIND_BY_ID_QUERY = 
    		"SELECT u FROM UserProject up " +
            "JOIN up.user u " +
            "JOIN up.project p " +
            "WHERE p.id =:projId and up.status= :userProjStatus";
    
    String FIND_BY_CRITERIA = 
    		"SELECT DISTINCT u " + 
    		"FROM UserSkill us " + 
    		"RIGHT OUTER JOIN us.user u " + 
    		"LEFT OUTER JOIN us.skill s " + 
    		"WHERE u.role = 'V'" + 
            "AND u.status = 'A'" + 
            "AND u.publishFlag = 'Y' " + 
    		"AND ((:keyWord is null " +
    		"OR LOWER(u.userName) LIKE LOWER(CONCAT('%', :keyWord, '%')) " + 
    		"OR LOWER(u.firstName) LIKE LOWER(CONCAT('%', :keyWord, '%')) " +
    		"OR LOWER(u.lastName) LIKE LOWER(CONCAT('%', :keyWord, '%')) " + 
            "OR LOWER(u.title) LIKE LOWER(CONCAT('%', :keyWord, '%')) " + 
            "OR LOWER(u.introduction) LIKE LOWER(CONCAT('%', :keyWord, '%')) " + 
            "OR LOWER(s.skillName) LIKE LOWER(CONCAT('%',:keyWord,'%'))) " +                 
            "AND (:skillCount = (select count(distinct us2.skill.id) from UserSkill us2 where us2.user.id=us.user.id and us2.skill.id in (:skills)) OR :skillCount=0)) " + 
            "ORDER BY u.createdTime DESC";
                
    String UPDATE_SLACK_STATUS = "UPDATE User u set u.chatFlag = :isSlackReg where u.id = :userId";
    
    String DELETE_USER_PROJECTS = "DELETE FROM UserProject up WHERE up.user.id=:userId";
    String DELETE_USER_SKILLS = "DELETE FROM UserSkill us WHERE us.user.id=:userId";    
    
    @Query(FIND_ACTIVE_VOLUNTEERS)
    Page<User> findActiveVolunteers(Pageable pageable);

    User findById(int id);
    
    User findByEmail(String email);
    
    List<User> findAllByOrderByIdDesc();    
  
    @Transactional
    @Modifying
    @Query(UPDATE_SLACK_STATUS)
    Integer updateIsSlackRegisteredFlag(@Param("isSlackReg") String isSlackReg, @Param("userId") Integer userId);
  
    @Query(FIND_BY_ID_QUERY)
    List<User> findByUserProjectId(@Param("projId") Integer projId, @Param("userProjStatus") String userProjStatus);

    @Query(FIND_BY_CRITERIA)
    List<User> findByKeyword(@Param("keyWord") String keyWord, @Param("skills") List<Integer> skills, @Param("skillCount") Long skillCount);
    
    @Modifying
    @Query(DELETE_USER_PROJECTS)
    @Transactional
    void deleteUserProjects(@Param("userId") Integer userId);

    @Modifying
    @Query(DELETE_USER_SKILLS)
    @Transactional
    void deleteUserSkills(@Param("userId") Integer userId);
    
}
