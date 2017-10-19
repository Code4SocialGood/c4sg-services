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
    
    String DELETE_USER_PROJECTS = "DELETE FROM UserProject up WHERE up.user.id=:userId";
    String DELETE_USER_SKILLS = "DELETE FROM UserSkill us WHERE us.user.id=:userId";    
    
    String SAVE_AVATAR = "UPDATE User u set u.avatarUrl = :imgUrl where u.id = :userId";
    
    /*String FIND_APPLICANT_QUERY =     
     		"SELECT upa.user_id, upa.project_id, u.first_name, u.last_name, u.title, upa.created_time as applied_time, upc.created_time as approved_time, upd.created_time as declined_time " +  
     		"FROM user u " + 
     		"LEFT OUTER JOIN user_project upa ON upa.user_id = u.id AND upa.project_id = :projectId AND upa.status = 'A' " +
     		"LEFT OUTER JOIN user_project upc ON upc.user_id = u.id AND upc.project_id = :projectId AND upc.status = 'C' " +
     		"LEFT OUTER JOIN user_project upd ON upd.user_id = u.id AND upd.project_id = :projectId AND upd.status = 'D' " +
     		"WHERE upa.project_id = :projectId " + 
            "ORDER BY upa.created_time DESC";*/
    
    
           
    String SEARCH_FIRST = 
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
    	                +   " AND (:status is null OR u.status = :status)"
    	                +   " AND (:role is null OR u.role = :role)"               
    	                +   " AND (:publishFlag is null OR u.publishFlag = :publishFlag)";
    
     String SEARCH_LAST = 
    		 ")  "
             + "ORDER BY u.createdTime DESC";
     
     String SEARCH_SKILL = " AND (us.skill.id in (:skills))";
     String SEARCH_JOB =  " AND ( u.jobTitleId in (:jobTitles))";
     
     String FIND_BY_KEYWORD = SEARCH_FIRST + SEARCH_LAST;		 
     String FIND_BY_KEYWORD_SKILL = SEARCH_FIRST + SEARCH_SKILL + SEARCH_LAST;
     String FIND_BY_KEYWORD_JOB = SEARCH_FIRST + SEARCH_JOB + SEARCH_LAST;
     String FIND_BY_KEYWORD_JOB_SKILL = SEARCH_FIRST + SEARCH_JOB + SEARCH_SKILL + SEARCH_LAST;
    
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
    
    @Query(FIND_BY_KEYWORD)
    List<User> findByKeyword(@Param("keyWord") String keyWord, @Param("status") String status, @Param("role") String role, @Param("publishFlag") String publishFlag);

    @Query(FIND_BY_KEYWORD_JOB)
    List<User> findByKeywordAndJob(@Param("keyWord") String keyWord, @Param("jobTitles") List<Integer> jobTitles, @Param("status") String status, @Param("role") String role, @Param("publishFlag") String publishFlag);

    @Query(FIND_BY_KEYWORD_SKILL)
    List<User> findByKeywordAndSkill(@Param("keyWord") String keyWord, @Param("skills") List<Integer> skills, @Param("status") String status, @Param("role") String role, @Param("publishFlag") String publishFlag);  

    @Query(FIND_BY_KEYWORD_JOB_SKILL)
    List<User> findByKeywordAndJobAndSkill(@Param("keyWord") String keyWord, @Param("jobTitles") List<Integer> jobTitles, @Param("skills") List<Integer> skills, @Param("status") String status, @Param("role") String role, @Param("publishFlag") String publishFlag);

    @Query(FIND_BY_KEYWORD)
    Page<User>  findByKeyword(@Param("keyWord") String keyWord, @Param("status") String status, @Param("role") String role, @Param("publishFlag") String publishFlag, Pageable pageable);

    @Query(FIND_BY_KEYWORD_JOB)
    Page<User>  findByKeywordAndJob(@Param("keyWord") String keyWord, @Param("jobTitles") List<Integer> jobTitles, @Param("status") String status, @Param("role") String role, @Param("publishFlag") String publishFlag, Pageable pageable);

    @Query(FIND_BY_KEYWORD_SKILL)
    Page<User>  findByKeywordAndSkill(@Param("keyWord") String keyWord, @Param("skills") List<Integer> skills, @Param("status") String status, @Param("role") String role, @Param("publishFlag") String publishFlag, Pageable pageable);  

    @Query(FIND_BY_KEYWORD_JOB_SKILL)
    Page<User>  findByKeywordAndJobAndSkill(@Param("keyWord") String keyWord, @Param("jobTitles") List<Integer> jobTitles, @Param("skills") List<Integer> skills, @Param("status") String status, @Param("role") String role, @Param("publishFlag") String publishFlag, Pageable pageable);

}