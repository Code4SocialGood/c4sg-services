package org.c4sg.dao;

import org.c4sg.entity.Organization;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.sql.Date;
import java.util.List;

import javax.transaction.Transactional;

public interface OrganizationDAO extends CrudRepository<Organization, Integer> {
    String FIND_BY_NAME_OR_DESCRIPTION = "SELECT o FROM Organization o " +
                                            "WHERE LOWER(o.name) LIKE LOWER(CONCAT('%', :name, '%')) " +
                                                "OR LOWER(o.description) LIKE LOWER(CONCAT('%', :description, '%')) order by project_updated_time desc";
    
    String UPDATE_PROJECT_UPDATED_TIME = "UPDATE Organization o SET o.projectUpdatedTime = :projectUpdatedTime where o.id = :orgId";

    Organization findByName(String name);

    List<Organization> findAll();

    Organization findOne(Integer id);

    /*@Query("SELECT o FROM Organization o WHERE LOWER(o.name) LIKE LOWER(CONCAT('%',:keyword,'%')) OR LOWER(o.description) LIKE LOWER(CONCAT('%',:keyword,'%'))")
    List<Organization> findByKeyword(String keyword);*/

    @Query(FIND_BY_NAME_OR_DESCRIPTION)
    List<Organization> findByNameOrDescription(@Param("name") String name, @Param("description") String description);

    
    @Transactional
    @Modifying
    @Query(UPDATE_PROJECT_UPDATED_TIME)
    Integer updateProjectUpdatedTime(@Param("projectUpdatedTime") Date projectUpdatedTime, @Param("orgId") Integer orgId);
 
    
    
//	List<Organization> findByNameLikeOrDescriptionLikeAllIgnoreCase(String name, String description);
}
