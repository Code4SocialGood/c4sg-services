package org.c4sg.dao;

import java.util.List;

import org.c4sg.entity.Organization;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface OrganizationDAO extends CrudRepository<Organization, Integer> {
    String FIND_BY_NAME_OR_DESCRIPTION = "SELECT o FROM Organization o " +
                                            "WHERE LOWER(o.name) LIKE LOWER(CONCAT('%', :name, '%')) " +
                                                "OR LOWER(o.description) LIKE LOWER(CONCAT('%', :description, '%')) order by project_updated_time desc";
    
    Organization findByName(String name);

    List<Organization> findAll();

    Organization findOne(Integer id);

    /*@Query("SELECT o FROM Organization o WHERE LOWER(o.name) LIKE LOWER(CONCAT('%',:keyword,'%')) OR LOWER(o.description) LIKE LOWER(CONCAT('%',:keyword,'%'))")
    List<Organization> findByKeyword(String keyword);*/

    @Query(FIND_BY_NAME_OR_DESCRIPTION)
    List<Organization> findByNameOrDescription(@Param("name") String name, @Param("description") String description);
    
//	List<Organization> findByNameLikeOrDescriptionLikeAllIgnoreCase(String name, String description);
}
