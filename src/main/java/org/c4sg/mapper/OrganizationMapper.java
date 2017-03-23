package org.c4sg.mapper;

import java.lang.reflect.Type;

import org.c4sg.dto.OrganizationDTO;
import org.c4sg.dto.OrganizationDTO;
import org.c4sg.entity.Organization;
import org.c4sg.entity.UserOrganization;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Component;

@Component
public class OrganizationMapper extends ModelMapper {

	public OrganizationDTO getOrganizationDtoFromEntity(Organization organization){
		OrganizationDTO organizationDTO = map(organization, OrganizationDTO.class);
		return organizationDTO;
	}
	
	public Organization getOrganizationEntityFromDto(OrganizationDTO organizationDTO){
		Organization organization = map(organizationDTO, Organization.class);
		return organization;
	}
	
	public OrganizationDTO getOrganizationDtoFromEntity(UserOrganization userOrganization){
		Type organizationTypeDTO = new TypeToken<OrganizationDTO>() {}.getType();
		OrganizationDTO organizationDTO = map(userOrganization.getOrganization(), organizationTypeDTO);
		organizationDTO.setStatus(userOrganization.getStatus());
		return organizationDTO;
	}
}
