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

	Project findById(int id);
	Project findByName(String name);
    List<Project> findAll();
	Project findByNameAndOrganizationId(String name, Integer orgId);
	
    @Query(FIND_BY_NAME_OR_DESCRIPTION)
    List<Project> findByNameOrDescription(@Param("name") String name, @Param("description") String description);

	@Query(FIND_BY_ORGANIZATION_ID)
	List<Project> getProjectsByOrganization(@Param("orgId") Integer orgId);

	@Query(FIND_BY_USER_ID)
	List<Project> findByUserId(@Param("userId") Integer userId);
	
	@Query(FIND_BY_USER_ID_AND_STATUS)
	List<Project> findByUserIdAndUserProjectStatus(@Param("userId") Integer userId, @Param("userProjectStatus") String userProjectStatus);
}
