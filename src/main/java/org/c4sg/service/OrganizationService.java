package org.c4sg.service;

import java.util.List;

import org.c4sg.dto.CreateOrganizationDTO;
import org.c4sg.dto.OrganizationDTO;
import org.c4sg.exception.UserOrganizationException;
import org.springframework.data.domain.Page;

public interface OrganizationService {

	void save(OrganizationDTO organizationDTO);
    void deleteOrganization(int id);
    OrganizationDTO findById(int id);
    OrganizationDTO createOrganization(CreateOrganizationDTO createOrganizationDTO);
    OrganizationDTO updateOrganization(int id, OrganizationDTO organizationDTO);
	OrganizationDTO saveUserOrganization(Integer userId, Integer organizationId)  throws UserOrganizationException;
    List<OrganizationDTO> findOrganizations();
    List<OrganizationDTO> findByKeyword(String name);
    Page<OrganizationDTO> findByCriteria(String keyWord, List<String> countries, Boolean open, String status, List<String> categories,Integer page,Integer size);
    List<OrganizationDTO> findByUser(Integer userId);
    void saveLogo(Integer id, String imgUrl);
    void approveOrDecline(Integer id, String status);
    int countByCountry();
}
