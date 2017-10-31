package org.c4sg.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.c4sg.entity.Organization;
import org.c4sg.entity.Project;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;


public interface OrganizationDAO extends CrudRepository<Organization, Integer> {
    String FIND_BY_NAME_OR_DESCRIPTION = "SELECT o FROM Organization o " +
                                            "WHERE LOWER(o.name) LIKE LOWER(CONCAT('%', :name, '%')) " +
                                                "OR LOWER(o.description) LIKE LOWER(CONCAT('%', :description, '%')) order by project_updated_time desc";

    String FIND_BY_CRITERIA_AND_COUNTRIES = "SELECT DISTINCT o FROM Project p RIGHT OUTER JOIN p.organization o" +
            " WHERE ((:keyword is null OR LOWER(o.name) LIKE LOWER(CONCAT('%', :keyword, '%'))" +
                " OR LOWER(o.description) LIKE LOWER(CONCAT('%', :keyword, '%')) OR LOWER(o.country) LIKE LOWER(CONCAT('%', :keyword, '%')))"
                + " AND (:status is null OR o.status = :status)"
                + " AND (:categories is null OR o.category in (:categories))"
                + " AND (o.country in (:countries))"
                + " AND (:open is null )"
                + ")  ORDER BY o.name ASC";
    
    String FIND_BY_CRITERIA_AND_COUNTRIES_AND_OPEN = "SELECT DISTINCT o FROM Project p JOIN p.organization o" +
            " WHERE ((:keyword is null OR LOWER(o.name) LIKE LOWER(CONCAT('%', :keyword, '%'))" +
                " OR LOWER(o.description) LIKE LOWER(CONCAT('%', :keyword, '%')) OR LOWER(o.country) LIKE LOWER(CONCAT('%', :keyword, '%')))"
                + " AND (:status is null OR o.status = :status)"
                + " AND (:categories is null OR o.category in (:categories))"
                + " AND (o.country in (:countries))"
                + " AND ((LOWER(p.status) ='a' AND :open=true) OR (LOWER(p.status) ='c' AND :open=false))"
                + ")  ORDER BY o.name ASC";
    
    String FIND_BY_CRITERIA = "SELECT DISTINCT o FROM Project p RIGHT OUTER JOIN p.organization o" +
            " WHERE ((:keyword is null OR LOWER(o.name) LIKE LOWER(CONCAT('%', :keyword, '%'))" +
                " OR LOWER(o.description) LIKE LOWER(CONCAT('%', :keyword, '%')) OR LOWER(o.country) LIKE LOWER(CONCAT('%', :keyword, '%')))"
                + " AND (:status is null OR o.status = :status)"
                + " AND (:categories is null OR o.category in (:categories))"                
                + " AND (:open is null )"
                + ")  ORDER BY o.name ASC";
    
    String FIND_BY_CRITERIA_AND_OPEN = "SELECT DISTINCT o FROM Project p JOIN p.organization o" +
            " WHERE ((:keyword is null OR LOWER(o.name) LIKE LOWER(CONCAT('%', :keyword, '%'))" +
                " OR LOWER(o.description) LIKE LOWER(CONCAT('%', :keyword, '%')) OR LOWER(o.country) LIKE LOWER(CONCAT('%', :keyword, '%')))"
                + " AND (:status is null OR o.status = :status)"
                + " AND (:categories is null OR o.category in (:categories))"                
                + " AND ((LOWER(p.status) ='a' AND :open=true) OR (LOWER(p.status) ='c' AND :open=false))"
                + ")  ORDER BY o.name ASC";

    String COUNT_BY_COUNTRY = "SELECT COUNT(DISTINCT country) " +
                                "FROM ( " +
                                    "    SELECT country FROM organization " +
                                    " UNION " +
                                    "    SELECT country from user " +
                                ") c WHERE country IS NOT NULL AND country <> ''";

    String DELETE_USER_ORGANIZATIONS = "DELETE FROM UserOrganization uo WHERE uo.organization.id=:id";
    
    String SAVE_LOGO = "UPDATE Organization o set o.logoUrl = :imgUrl where o.id = :organizationId";
    
    String APPROVE_DECLINE = "UPDATE Organization o set o.status = :status where o.id = :organizationId";
    
    Organization findByName(String name);

    List<Organization> findAllByOrderByIdDesc();
    
    Organization findOne(Integer id);

    @Query(value = COUNT_BY_COUNTRY, nativeQuery = true)
    int countByCountry();

    /*@Query("SELECT o FROM Organization o WHERE LOWER(o.name) LIKE LOWER(CONCAT('%',:keyword,'%')) OR LOWER(o.description) LIKE LOWER(CONCAT('%',:keyword,'%'))")
    List<Organization> findByKeyword(String keyword);*/

    @Query(FIND_BY_NAME_OR_DESCRIPTION)
    List<Organization> findByNameOrDescription(@Param("name") String name, @Param("description") String description);

    @Query(FIND_BY_CRITERIA_AND_COUNTRIES)
    Page<Organization> findByCriteriaAndCountries(@Param("keyword") String keyWord, @Param("countries") List<String> countries,@Param("open") Boolean open
    		, @Param("status") String status, @Param("categories") List<String> categories,Pageable pageable);
    
    @Query(FIND_BY_CRITERIA_AND_COUNTRIES_AND_OPEN)
    Page<Organization> findByCriteriaAndCountriesAndOpen(@Param("keyword") String keyWord, @Param("countries") List<String> countries,@Param("open") Boolean open
    		, @Param("status") String status, @Param("categories") List<String> categories,Pageable pageable);
    
    @Query(FIND_BY_CRITERIA)
    Page<Organization> findByCriteria(@Param("keyword") String keyWord, @Param("open") Boolean open
    		, @Param("status") String status, @Param("categories") List<String> categories,Pageable pageable);
    
    @Query(FIND_BY_CRITERIA_AND_OPEN)
    Page<Organization> findByCriteriaAndOpen(@Param("keyword") String keyWord, @Param("open") Boolean open
    		, @Param("status") String status, @Param("categories") List<String> categories, Pageable pageable);

    @Query(FIND_BY_CRITERIA_AND_COUNTRIES)
    List<Organization> findByCriteriaAndCountries(@Param("keyword") String keyWord, @Param("countries") List<String> countries,@Param("open") Boolean open
    		, @Param("status") String status, @Param("categories") List<String> categories);
    
    @Query(FIND_BY_CRITERIA_AND_COUNTRIES_AND_OPEN)
    List<Organization> findByCriteriaAndCountriesAndOpen(@Param("keyword") String keyWord, @Param("countries") List<String> countries,@Param("open") Boolean open
    		, @Param("status") String status, @Param("categories") List<String> categories);
    
    @Query(FIND_BY_CRITERIA)
    List<Organization> findByCriteria(@Param("keyword") String keyWord, @Param("open") Boolean open
    		, @Param("status") String status, @Param("categories") List<String> categories);
    
    @Query(FIND_BY_CRITERIA_AND_OPEN)
    List<Organization> findByCriteriaAndOpen(@Param("keyword") String keyWord, @Param("open") Boolean open
    		, @Param("status") String status, @Param("categories") List<String> categories);
    
    @Modifying
    @Query(DELETE_USER_ORGANIZATIONS)
    @Transactional
    void deleteUserOrganizations(@Param("id") Integer id);

//	List<Organization> findByNameLikeOrDescriptionLikeAllIgnoreCase(String name, String description);
    
    @Transactional
    @Modifying
    @Query(SAVE_LOGO)
    void updateLogo(@Param("imgUrl") String imgUrl, @Param("organizationId") Integer organizationId);
   
    @Transactional
    @Modifying
    @Query(APPROVE_DECLINE)
    void approveOrDecline(@Param("organizationId") Integer organizationId, @Param("status") String status);

}
