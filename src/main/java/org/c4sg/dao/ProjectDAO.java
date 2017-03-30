package org.c4sg.dao;

import org.c4sg.entity.Project;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProjectDAO extends CrudRepository<Project, Long> {

    String FIND_BY_USER_STATUS_QUERY = "SELECT p FROM UserProject up " +
                                            "JOIN up.project p " +
                                                "WHERE up.user.id = :userId AND up.status = :status ";

<<<<<<< HEAD
    String FIND_BY_KEYWORD = "SELECT p FROM Project p WHERE p.name LIKE %:keyword% OR p.description LIKE %:keyword% ORDER BY p.createdTime DESC";    
    
    String FIND_BY_NAME_ORGANIZATION_ID = "SELECT p FROM Project p WHERE p.name =:name AND p.organization.id=:orgId";
=======
    String FIND_BY_ORGANIZATION_ID = "SELECT p FROM Project p WHERE p.organization.id = :orgId";

    String FIND_BY_NAME_OR_DESCRIPTION = "SELECT p FROM Project p " +
                                            "WHERE p.name LIKE CONCAT('%', :name, '%') " +
                                                "OR p.description LIKE CONCAT('%', :description, '%') ORDER BY p.createdTime DESC";

>>>>>>> refs/remotes/Code4SocialGood/master
	Project findById(int id);
	Project findByName(String name);
    List<Project> findAll();

    @Query(FIND_BY_NAME_OR_DESCRIPTION)
    List<Project> findByNameOrDescription(@Param("name") String name, @Param("description") String description);

	@Query(FIND_BY_ORGANIZATION_ID)
	List<Project> getProjectsByOrganization(@Param("orgId") Integer orgId);

	@Query(FIND_BY_USER_STATUS_QUERY)
	List<Project> findByStatus(@Param("userId") Integer userId, @Param("status") String status);

<<<<<<< HEAD
	@Query(FIND_BY_KEYWORD)
	List<Project> findByKeyword(@Param("keyword") String keyword);
	
	@Query(FIND_BY_NAME_ORGANIZATION_ID)
	Project findByNameOrganizationId(@Param("name")String name,  @Param("orgId") Integer orgId);	
=======
>>>>>>> refs/remotes/Code4SocialGood/master
}
