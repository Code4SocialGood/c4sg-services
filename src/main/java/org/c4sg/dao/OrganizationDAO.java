package org.c4sg.dao;

import java.util.List;

import org.c4sg.entity.Organization;
import org.c4sg.entity.Project;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface OrganizationDAO extends CrudRepository<Organization, Integer> {
    String FIND_BY_NAME_OR_DESCRIPTION = "SELECT o FROM Organization o " +
                                            "WHERE LOWER(o.name) LIKE LOWER(CONCAT('%', :name, '%')) " +
                                                "OR LOWER(o.description) LIKE LOWER(CONCAT('%', :description, '%')) order by project_updated_time desc";

    String FIND_BY_CRITERIA = "SELECT DISTINCT o FROM Project p RIGHT OUTER JOIN p.organization o " +
            "WHERE ((LOWER(o.name) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
                "OR LOWER(o.description) LIKE LOWER(CONCAT('%', :keyword, '%')) OR LOWER(o.country) LIKE LOWER(CONCAT('%', :keyword, '%')))"
                + " AND LOWER(o.country) LIKE LOWER(CONCAT('%', :country, '%')) "
                + "AND ((LOWER(p.status) ='a' AND :open=true) OR (LOWER(p.status) ='c' AND :open=false))"
                + ") OR (:keyword is null AND :country is null AND :open<>true) ORDER BY o.name ASC";

    Organization findByName(String name);

    List<Organization> findAllByOrderByIdDesc();
    
    Organization findOne(Integer id);

    /*@Query("SELECT o FROM Organization o WHERE LOWER(o.name) LIKE LOWER(CONCAT('%',:keyword,'%')) OR LOWER(o.description) LIKE LOWER(CONCAT('%',:keyword,'%'))")
    List<Organization> findByKeyword(String keyword);*/

    @Query(FIND_BY_NAME_OR_DESCRIPTION)
    List<Organization> findByNameOrDescription(@Param("name") String name, @Param("description") String description);

    @Query(FIND_BY_CRITERIA)
    List<Organization> findByCriteria(@Param("keyword") String keyWord, @Param("country") String country,@Param("open") boolean open);

//	List<Organization> findByNameLikeOrDescriptionLikeAllIgnoreCase(String name, String description);
}
