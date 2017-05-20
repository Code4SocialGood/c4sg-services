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

    String FIND_BY_KEYWORD = "SELECT DISTINCT o FROM Project p RIGHT OUTER JOIN p.organization o " +
            "WHERE ((:keyword is null OR LOWER(o.name) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
                "OR LOWER(o.description) LIKE LOWER(CONCAT('%', :keyword, '%')) " + 
                "OR LOWER(o.state) LIKE LOWER(CONCAT('%', :keyword, '%'))" + 
                "OR LOWER(o.country) LIKE LOWER(CONCAT('%', :keyword, '%'))))" +
                "AND (:status is null OR o.status = :status) "+
                "AND (:category is null OR o.category = :category) "+
                "AND p.id is null "+
                "  ORDER BY o.name ASC";
    
    String FIND_BY_KEYWORD_OPORTUNITES = "SELECT DISTINCT o FROM Project p INNER JOIN p.organization o " +
            "WHERE ((:keyword is null OR LOWER(o.name) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
                "OR LOWER(o.description) LIKE LOWER(CONCAT('%', :keyword, '%')) " + 
                "OR LOWER(o.state) LIKE LOWER(CONCAT('%', :keyword, '%'))" + 
                "OR LOWER(o.country) LIKE LOWER(CONCAT('%', :keyword, '%'))))" +
                "AND (:status is null OR o.status = :status) "+
                "AND (:category is null OR o.category = :category) "+
                "  ORDER BY o.name ASC";
    
    String FIND_BY_KEYWORD_COUNTRIES = "SELECT DISTINCT o FROM Project p RIGHT OUTER JOIN p.organization o " +
            "WHERE ((:keyword is null OR LOWER(o.name) LIKE LOWER(CONCAT('%', :keyword, '%'))) "+ 
            "OR LOWER(o.description) LIKE LOWER(CONCAT('%', :keyword, '%'))" + 
            "OR LOWER(o.state) LIKE LOWER(CONCAT('%', :keyword, '%'))"+
            "OR LOWER(o.country) LIKE LOWER(CONCAT('%', :keyword, '%')))" + 
            "AND (o.country in (:country))" +
            "AND (:status is null OR o.status = :status) "+
            "AND (:category is null OR o.category = :category) " +
            "AND p.id is null "+
            " ORDER BY o.name ASC";
    
    String FIND_BY_KEYWORD_COUNTRIES_OPORTUNITES = "SELECT DISTINCT o FROM Project p INNER JOIN p.organization o " +
            "WHERE ((:keyword is null OR LOWER(o.name) LIKE LOWER(CONCAT('%', :keyword, '%'))) "+ 
            "OR LOWER(o.description) LIKE LOWER(CONCAT('%', :keyword, '%'))" + 
            "OR LOWER(o.state) LIKE LOWER(CONCAT('%', :keyword, '%'))"+
            "OR LOWER(o.country) LIKE LOWER(CONCAT('%', :keyword, '%')))" + 
            "AND (o.country in (:country))" +
            "AND (:status is null OR o.status = :status) "+
            "AND (:category is null OR o.category = :category)" +
            " ORDER BY o.name ASC" ;

    Organization findByName(String name);

    List<Organization> findAllByOrderByIdDesc();
    
    Organization findOne(Integer id);

    @Query(FIND_BY_NAME_OR_DESCRIPTION)
    List<Organization> findByNameOrDescription(@Param("name") String name, @Param("description") String description);

    @Query(FIND_BY_KEYWORD)
    List<Organization> findByKeyWord(@Param("keyword") String keyWord, @Param("status") String status, @Param("category") String category);
    
    @Query(FIND_BY_KEYWORD_OPORTUNITES)
	  List<Organization> findByKeyWordOportunites(@Param("keyword") String keyWord, @Param("status")String status, @Param("category") String category);
    
    @Query(FIND_BY_KEYWORD_COUNTRIES)
    List<Organization> findByKeyWordCountries(@Param("keyword") String keyWord, @Param("country") List<String> country, @Param("status") String status, @Param("category") String category);
    
    @Query(FIND_BY_KEYWORD_COUNTRIES_OPORTUNITES)
	  List<Organization> findByKeyWordCountriesOportunites(@Param("keyword") String keyWord, @Param("country") List<String> countries, @Param("status") String status, @Param("category") String category);
  
    @Modifying
    @Query(DELETE_USER_ORGANIZATIONS)
    @Transactional
    void deleteUserOrganizations(@Param("id") Integer id);
//	List<Organization> findByNameLikeOrDescriptionLikeAllIgnoreCase(String name, String description);
}

