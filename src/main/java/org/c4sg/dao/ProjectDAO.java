package org.c4sg.dao;

import org.c4sg.entity.Project;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProjectDAO extends CrudRepository<Project, Long> {

    String FIND_BY_USER_ID = 
    		"SELECT p FROM UserProject up " +
        	"JOIN up.project p " +
            "WHERE up.user.id = :userId " +
            "ORDER BY up.createdTime DESC";

    String FIND_BY_USER_ID_AND_STATUS = 
    		"SELECT p FROM UserProject up " +
            "JOIN up.project p " +
            "WHERE up.user.id = :userId AND up.status = :userProjectStatus " +
            "ORDER BY up.createdTime DESC";

	// TODO JW order by
    String FIND_BY_ORGANIZATION_ID = 
    		"SELECT p FROM Project p " +
    		"WHERE p.organization.id = :orgId " +
            "ORDER BY p.createdTime DESC";

    String FIND_BY_NAME_OR_DESCRIPTION = 
    		"SELECT p FROM Project p " +
            "WHERE p.name LIKE CONCAT('%', :name, '%') " +
            "OR p.description LIKE CONCAT('%', :description, '%') " +
            "ORDER BY p.createdTime DESC";

    String FIND_ACTIVE_BY_CRITERIA = 
    		"SELECT DISTINCT p "
    		+ "FROM ProjectSkill ps "
    		+ "RIGHT OUTER JOIN ps.project p "
    		+ "LEFT OUTER JOIN ps.skill s "
            + "WHERE ("
            + 	"(:keyWord is null OR LOWER(p.name) LIKE LOWER(CONCAT('%', :keyWord, '%'))" 
            +   " OR LOWER(p.description) LIKE LOWER(CONCAT('%', :keyWord, '%')) OR LOWER(s.skillName) LIKE LOWER(CONCAT('%',:keyWord,'%')))"          
            +   " AND (:skillCount = (select count(distinct ps2.skill.id) from ProjectSkill ps2 where ps2.project.id=ps.project.id and ps2.skill.id in (:skills)) OR :skillCount=0)" 
            +   " AND p.status = 'A'"
            +   ")  "
            + "ORDER BY p.createdTime DESC";

    
	Project findById(int id);
	Project findByName(String name);

    List<Project> findAllByOrderByIdDesc();
	Project findByNameAndOrganizationId(String name, Integer orgId);
	
    @Query(FIND_BY_NAME_OR_DESCRIPTION)
    List<Project> findByNameOrDescription(@Param("name") String name, @Param("description") String description);

    @Query(FIND_ACTIVE_BY_CRITERIA)
    List<Project> findByKeyword(@Param("keyWord") String keyWord, @Param("skills") List<Integer> skills, @Param("skillCount") Long skillCount);

	@Query(FIND_BY_ORGANIZATION_ID)
	List<Project> getProjectsByOrganization(@Param("orgId") Integer orgId);

	@Query(FIND_BY_USER_ID)
	List<Project> findByUserId(@Param("userId") Integer userId);
	
	@Query(FIND_BY_USER_ID_AND_STATUS)
	List<Project> findByUserIdAndUserProjectStatus(@Param("userId") Integer userId, @Param("userProjectStatus") String userProjectStatus);
}
