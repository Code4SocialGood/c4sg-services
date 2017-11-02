package org.c4sg.controller;

import java.net.URI;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;
import javax.validation.constraints.Pattern;

import org.c4sg.constraint.ListEntry;
import org.c4sg.dto.CreateOrganizationDTO;
import org.c4sg.dto.OrganizationDTO;
import org.c4sg.exception.BadRequestException;
import org.c4sg.exception.NotFoundException;
import org.c4sg.exception.UserOrganizationException;
import org.c4sg.service.OrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@CrossOrigin
@RestController
@RequestMapping("/api/organizations")
@Api(description = "Operations about Organizations", tags = "organization")
@Validated
public class OrganizationController {

	@Autowired
	private OrganizationService organizationService;

	@CrossOrigin
	@RequestMapping(produces = { "application/json" }, method = RequestMethod.GET)
	@ApiOperation(value = "Find all organizations", notes = "Returns a collection of organizations")
	public List<OrganizationDTO> getOrganizations() {
		
    	System.out.println("************** OrganizationController.getOrganizations() **************");
    	
		return organizationService.findOrganizations();
	}

	@CrossOrigin
	@RequestMapping(value = "/{id}", produces = { "application/json" }, method = RequestMethod.GET)
	@ApiOperation(value = "Find organization by ID", notes = "Returns a collection of organizations")
	public OrganizationDTO getOrganization(
			@ApiParam(value = "ID of organization to return", required = true) 
			@PathVariable("id") int id) {
		
    	System.out.println("************** OrganizationController.getOrganization()" 
                + ": id=" + id 
                + " **************");
    	
		return organizationService.findById(id);
	}

	@CrossOrigin
	@RequestMapping(value = "/search", produces = { "application/json" }, method = RequestMethod.GET)
	@ApiOperation(value = "Find organization by keyWord", notes = " Returns a list of organizations which has the keyword in name / description / country, AND, which has the opportunities open, AND, which is located in the selected country. The search result is sorted by organization name in ascending order.")
	public Page<OrganizationDTO> getOrganizations (
			@ApiParam(value = "Keyword in Name or description or country of organization to return", required = false) @RequestParam(required = false) String keyWord,
			@ApiParam(value = "Countries of organization to return", required = false) @RequestParam(required = false) List<String> countries,
			@ApiParam(value = "Opportunities open in the organization", required = false) @RequestParam(required = false) Boolean open,
			@ApiParam(value = "Status of the organization to return", required = false) @Pattern(regexp="[ADPNC]") @RequestParam(required = false) String status,
			@ApiParam(value = "Category of the organization to return", required = false) @ListEntry @RequestParam(required = false) List<String> category,
		    @ApiParam(value = "Results page you want to retrieve (0..N)",required=false) @RequestParam(required=false) Integer page,
		    @ApiParam(value = "Number of records per page", required=false) @RequestParam(required=false) Integer size)	{
		
    	System.out.println("************** OrganizationController.getOrganizations()" 
                + ": keyWord=" + keyWord 
                + "; countries=" + countries 
                + "; open=" + open 
                + "; status=" + status 
                + "; category=" + category 
                + "; page=" + page 
                + "; size=" + size                 
                + " **************");
    	
		try {
			return organizationService.findByCriteria(keyWord, countries, open, status, category,page,size);
		} catch (Exception e) {
			throw new BadRequestException(e.getMessage());
		}
	}

	@CrossOrigin
	@RequestMapping(method = RequestMethod.POST)
	@ApiOperation(value = "Create organization", notes = "Creates an organization, and returns the organization created.", response = OrganizationDTO.class)
	@ApiResponses(value = { @ApiResponse(code = 500, message = "Internal server error") })
	public Map<String, Object> createOrganization(
			@ApiParam(value = "Organization to create", required = true) @RequestBody @Valid CreateOrganizationDTO createOrganizationDTO) {

    	System.out.println("************** OrganizationController.createOrganization()" 
                + ": createOrganizationDTO=" + createOrganizationDTO                
                + " **************");
    	
		Map<String, Object> responseData = null;
		// organizationDTO.setLogo(organizationService.getLogoUploadPath(organizationDTO.getId()));
		try {
			OrganizationDTO createdOrganization = organizationService.createOrganization(createOrganizationDTO);
			responseData = Collections.synchronizedMap(new HashMap<>());
			responseData.put("organization", createdOrganization);
		} catch (Exception e) {
			System.err.println(e);
		}

		return responseData;
	}

	@CrossOrigin
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	@ApiOperation(value = "Update an existing organization")
	public Map<String, Object> updateOrganization (
			@ApiParam(value = "Updated organization object", required = true) @PathVariable("id") int id,
			@RequestBody @Valid OrganizationDTO organizationDTO) {

    	System.out.println("************** OrganizationController.updateOrganization()" 
                + ": id=" + id  
                + "; organizationDTO=" + organizationDTO 
                + " **************");
    	
		Map<String, Object> responseData = null;
		try {
			OrganizationDTO updatedOrganization = organizationService.updateOrganization(id, organizationDTO);
			responseData = Collections.synchronizedMap(new HashMap<>());
			responseData.put("organization", updatedOrganization);
		} catch (Exception e) {
			System.err.println(e);
		}
		return responseData;
	}

	@CrossOrigin
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	@ApiOperation(value = "Deletes a organization")
	public void deleteOrganization(
			@ApiParam(value = "Organization id to delete", required = true) @PathVariable("id") int id) {
		
    	System.out.println("************** OrganizationController.deleteOrganization()" 
                + ": id=" + id  
                + " **************");

		try {
			organizationService.deleteOrganization(id);
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	@CrossOrigin
	@RequestMapping(value = "/user/{id}", method = RequestMethod.GET)
	@ApiOperation(value = "Find organizations by user id", notes = "Returns a collection of organizations")
	@ApiResponses(value = { @ApiResponse(code = 404, message = "ID of user invalid") })
	public List<OrganizationDTO> getOrganizationsByUser(
			@ApiParam(value = "userId of organizations to return", required = true) @PathVariable("id") Integer id) {

    	System.out.println("************** OrganizationController.getOrganizationsByUser()" 
                + ": id=" + id  
                + " **************");
    	
		List<OrganizationDTO> organizations = null;
		try {
			organizations = organizationService.findByUser(id);
		} catch (Exception e) {
			throw new NotFoundException("ID of user invalid");
		}

		return organizations;
	}

	@CrossOrigin
	@RequestMapping(value = "/{id}/users/{userId}", method = RequestMethod.POST)
	@ApiOperation(value = "Create a relation between user and organization")
	@ApiResponses(value = { @ApiResponse(code = 404, message = "ID of organization or user invalid") })
	// TODO: Replace explicit user{id} with AuthN user id.
	public ResponseEntity<?> createUserOrganization(
			@ApiParam(value = "ID of user", required = true) @PathVariable("userId") Integer userId,
			@ApiParam(value = "ID of organization", required = true) @PathVariable("id") Integer organizationId) {
		
    	System.out.println("************** OrganizationController.createUserOrganization()" 
                + ": userId=" + userId  
                + "; organizationId=" + organizationId  
                + " **************");
    	
		try {
			organizationService.saveUserOrganization(userId, organizationId);
			URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}/users/{userId}")
					.buildAndExpand(organizationId, userId).toUri();
			return ResponseEntity.created(location).build();
		} catch (NullPointerException | UserOrganizationException e) {
			throw new NotFoundException("ID of organization or user invalid, or relationship already exist");
		}
	}
	
    @CrossOrigin
    @RequestMapping(value = "/{id}/logo", params = "imgUrl", method = RequestMethod.PUT)
	@ApiOperation(value = "Upload an organization logo image")
	public void saveLogo(
			@ApiParam(value = "organization Id", required = true) @PathVariable("id") Integer id,
			@ApiParam(value = "Image Url", required = true) @RequestParam("imgUrl") String url) {

    	System.out.println("************** OrganizationController.saveLogo()" 
                + ": id=" + id  
                + "; url=" + url  
                + " **************");
    	
    	organizationService.saveLogo(id, url);
	}
    
    @CrossOrigin
    @RequestMapping(value = "/{id}/approve", params = "status", method = RequestMethod.PUT)
	@ApiOperation(value = "Approve an organization")
	public void approve(
			@ApiParam(value = "organization Id", required = true) @PathVariable("id") Integer id,
			@ApiParam(value = "status", required = true) @RequestParam("status") String status) {

    	System.out.println("************** OrganizationController.saveLogo()" 
                + ": id=" + id  
                + "; status=" + status  
                + " **************");
    	
    	organizationService.approveOrDecline(id, status);
	}

	@CrossOrigin
	@RequestMapping(value="/countries/total", method = RequestMethod.GET)
	@ApiOperation(value = "Total number of countries (Organizations & Users) involved in the project")
	public ResponseEntity<Map<String, String>> getJobTitles() {
		HashMap<String, String> map = new HashMap<>();
		int totalCountries = organizationService.countByCountry();
		map.put("total", String.valueOf(totalCountries));

		return new ResponseEntity<>(map, HttpStatus.OK);
	}
}