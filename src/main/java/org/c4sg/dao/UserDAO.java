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
    
    String FIND_BY_NOTIFY = 
    		"SELECT u FROM User u " +
            "WHERE u.status = 'A' and u.role = 'V' and u.notifyFlag = 'Y'";
    
    String FIND_BY_ID_QUERY = 
    		"SELECT u FROM UserProject up " +
            "JOIN up.user u " +
            "JOIN up.project p " +
            "WHERE p.id =:projId and up.status= :userProjStatus";

    String FIND_BY_ORG_ID = 
    		"SELECT u FROM UserOrganization uo " +
            "JOIN uo.user u " +
            "JOIN uo.organization o " +
            "WHERE o.id =:orgId";

    String FIND_BY_KEYWORD_SKILL_CRITERIA = 
    		"SELECT DISTINCT u"
    	       	    +   " FROM UserSkill us"
    	       	    +   " RIGHT OUTER JOIN us.user u" 
    	       	    +   " LEFT OUTER JOIN us.skill s"		
    	       	    +   " WHERE ("
    	       	    	+ 	"(:keyWord is null OR LOWER(u.userName) LIKE LOWER(CONCAT('%', :keyWord, '%'))" 
    	       	    	+   " OR LOWER(u.firstName) LIKE LOWER(CONCAT('%', :keyWord, '%'))"
    	       	    	+   " OR LOWER(u.lastName) LIKE LOWER(CONCAT('%', :keyWord, '%'))"
    	               +   " OR LOWER(u.title) LIKE LOWER(CONCAT('%', :keyWord, '%'))"
    	               +   " OR LOWER(u.introduction) LIKE LOWER(CONCAT('%', :keyWord, '%'))"
    	               +	" OR LOWER(u.state) LIKE LOWER(CONCAT('%', :keyWord, '%'))"
    	               +   " OR LOWER(u.country) LIKE LOWER(CONCAT('%', :keyWord, '%'))"           
    	               +   " OR LOWER(s.skillName) LIKE LOWER(CONCAT('%',:keyWord,'%')))"
    	               +   " AND (us.skill.id in (:skills))"
    	               //+   " AND (:skillCount = (select count(distinct ps2.skill.id) from ProjectSkill ps2 where ps2.project.id=ps.project.id and ps2.skill.id in (:skills)) OR :skillCount=0)" 
    	               +   " AND (:status is null OR u.status = :status)"
    	               +   " AND (:role is null OR u.role = :role)"
    	               +   " AND (:publishFlag is null OR u.publishFlag = :publishFlag)"
    	               +   ")  "
    	               + "ORDER BY u.createdTime DESC";
    	                
     String FIND_BY_KEYWORD_CRITERIA = 
       	"SELECT DISTINCT u"
       	    +   " FROM UserSkill us"
       	    +   " RIGHT OUTER JOIN us.user u" 
       	    +   " LEFT OUTER JOIN us.skill s"		
       	    +   " WHERE ("
       	    	+ 	"(:keyWord is null OR LOWER(u.userName) LIKE LOWER(CONCAT('%', :keyWord, '%'))" 
       	    	+   " OR LOWER(u.firstName) LIKE LOWER(CONCAT('%', :keyWord, '%'))"
       	    	+   " OR LOWER(u.lastName) LIKE LOWER(CONCAT('%', :keyWord, '%'))"
               +   " OR LOWER(u.title) LIKE LOWER(CONCAT('%', :keyWord, '%'))"
               +   " OR LOWER(u.introduction) LIKE LOWER(CONCAT('%', :keyWord, '%'))"
               +	" OR LOWER(u.state) LIKE LOWER(CONCAT('%', :keyWord, '%'))"
               +   " OR LOWER(u.country) LIKE LOWER(CONCAT('%', :keyWord, '%'))"           
               +   " OR LOWER(s.skillName) LIKE LOWER(CONCAT('%',:keyWord,'%')))"             
               //+   " AND (:skillCount = (select count(distinct ps2.skill.id) from ProjectSkill ps2 where ps2.project.id=ps.project.id and ps2.skill.id in (:skills)) OR :skillCount=0)" 
               +   " AND (:status is null OR u.status = :status)"
               +   " AND (:role is null OR u.role = :role)"               +   " AND (:publishFlag is null OR u.publishFlag = :publishFlag)"
               +   ")  "
               + "ORDER BY u.createdTime DESC";
    
     String FIND_APPLICANT_QUERY =     
     		"SELECT upa.user_id, upa.project_id, u.first_name, u.last_name, u.title, upa.created_time as applied_time, upc.created_time as approved_time, upd.created_time as declined_time " +  
     		"FROM user u " + 
     		"LEFT OUTER JOIN user_project upa ON u.id = upa.user_id AND upa.status = 'A' " +
     		"LEFT OUTER JOIN user_project upc ON u.id = upc.user_id AND upc.status = 'C' " +
     		"LEFT OUTER JOIN user_project upd ON u.id = upd.user_id AND upd.status = 'D' " +
     		"WHERE upa.project_id = :projectId";
     
    String DELETE_USER_PROJECTS = "DELETE FROM UserProject up WHERE up.user.id=:userId";
    String DELETE_USER_SKILLS = "DELETE FROM UserSkill us WHERE us.user.id=:userId";    
    
    String SAVE_AVATAR = "UPDATE User u set u.avatarUrl = :imgUrl where u.id = :userId";
    
    // String UPDATE_SLACK_STATUS = "UPDATE User u set u.chatUserName = u.userName where u.id = :userId";
    
    @Query(FIND_ACTIVE_VOLUNTEERS)
    Page<User> findActiveVolunteers(Pageable pageable);

    @Query(FIND_BY_NOTIFY)
    List<User> findByNotify();
    
    User findById(int id);
    
    User findByEmail(String email);
    
    List<User> findAllByOrderByIdDesc();    
    
    @Query(FIND_BY_ID_QUERY)
    List<User> findByUserProjectId(@Param("projId") Integer projId, @Param("userProjStatus") String userProjStatus);
    
    @Query(FIND_BY_ORG_ID)
    List<User> findByOrgId(@Param("orgId") Integer orgId);

    @Query(FIND_BY_KEYWORD_SKILL_CRITERIA)
    Page<User> findByKeywordAndSkill(@Param("keyWord") String keyWord, @Param("skills") List<Integer> skills, @Param("status") String status, @Param("role") String role, @Param("publishFlag") String publishFlag, Pageable pageable);
    
    @Query(FIND_BY_KEYWORD_CRITERIA)
    Page<User> findByKeyword(@Param("keyWord") String keyWord, @Param("status") String status, @Param("role") String role, @Param("publishFlag") String publishFlag,Pageable pageable);
    
    @Query(FIND_BY_KEYWORD_SKILL_CRITERIA)
    List<User> findByKeywordAndSkill(@Param("keyWord") String keyWord, @Param("skills") List<Integer> skills, @Param("status") String status, @Param("role") String role, @Param("publishFlag") String publishFlag);
    
    @Query(FIND_BY_KEYWORD_CRITERIA)
    List<User> findByKeyword(@Param("keyWord") String keyWord, @Param("status") String status, @Param("role") String role, @Param("publishFlag") String publishFlag);

    @Modifying
    @Query(DELETE_USER_PROJECTS)
    @Transactional
    void deleteUserProjects(@Param("userId") Integer userId);

    @Modifying
    @Query(DELETE_USER_SKILLS)
    @Transactional
    void deleteUserSkills(@Param("userId") Integer userId);
 
    @Transactional
    @Modifying
    @Query(SAVE_AVATAR)
    void updateAvatar(@Param("imgUrl") String imgUrl, @Param("userId") Integer userId);

    // Native query for temporal table
    @Query(value = FIND_APPLICANT_QUERY, nativeQuery = true)
    List<Object[]> findApplicants(@Param("projectId") Integer projectId);
    
    //@Query(FIND_APPLICANT_QUERY)
    //List<Applicant> findApplicants(@Param("projectId") Integer projectId);
    
    /*
    @Transactional
    @Modifying
    @Query(UPDATE_SLACK_STATUS)
    Integer updateIsSlackRegisteredFlag(@Param("isSlackReg") String isSlackReg, @Param("userId") Integer userId);
    */
}
