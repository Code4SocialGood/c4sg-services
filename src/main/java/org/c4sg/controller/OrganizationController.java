package org.c4sg.controller;

import static org.c4sg.constant.Directory.LOGO_UPLOAD;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.apache.tomcat.util.codec.binary.Base64;
import org.c4sg.dto.CreateOrganizationDTO;
import org.c4sg.dto.OrganizationDTO;
import org.c4sg.exception.NotFoundException;
import org.c4sg.exception.UserOrganizationException;
import org.c4sg.service.OrganizationService;
import org.c4sg.util.FileUploadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
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
public class OrganizationController {

	@Autowired
	private OrganizationService organizationService;
	
	@RequestMapping(value = "/{id}/logo", method = RequestMethod.POST)
	@ApiOperation(value = "Upload Logo as Image File")
	public String uploadLogo(@ApiParam(value = "Organization Id", required = true) @PathVariable Integer id,
			                 @ApiParam(value = "Image File", required = true) @RequestPart("file") MultipartFile file) {
		String contentType = file.getContentType();
    
		if (!FileUploadUtil.isValidImageFile(contentType)) {
			return "Invalid Image file! Content Type :-" + contentType;
		}
		File directory = new File(LOGO_UPLOAD.getValue());
		if (!directory.exists()) {
			directory.mkdir();
		}
		File f = new File(organizationService.getLogoUploadPath(id));
		try (FileOutputStream fos = new FileOutputStream(f)) {
			byte[] imageByte = file.getBytes();
			fos.write(imageByte);
			return "Success";
		} catch (Exception e) {
			return "Error saving logo for organization " + id + " : " + e;
		}
	}
  
    @CrossOrigin
    @RequestMapping(produces = {"application/json"}, method = RequestMethod.GET)
    @ApiOperation(value = "Find all organizations", notes = "Returns a collection of organizations")
    public List<OrganizationDTO> getOrganizations() {
        return organizationService.findOrganizations();
    }

    @CrossOrigin
    @RequestMapping(value = "/{id}", produces = {"application/json"}, method = RequestMethod.GET)
    @ApiOperation(value = "Find organization by ID", notes = "Returns a collection of organizations")
    public OrganizationDTO getOrganization(@ApiParam(value = "ID of organization to return", required = true)
                                           @PathVariable("id") int id) {
        return organizationService.findById(id);
    }

    @CrossOrigin
    @RequestMapping(value = "/search", produces = {"application/json"}, method = RequestMethod.GET)
    @ApiOperation(value = "Find organization by keyWord", notes = " Returns a list of organizations which has the keyword in name / description / country, AND, which has the opportunities open, AND, which is located in the selected country. The search result is sorted by organization name in ascending order.")
    public List<OrganizationDTO> getOrganizations(@ApiParam(value = "Keyword in Name, description, state or country of organization to return", required = false)
                                               		@RequestParam(required=false) String keyWord,
                                               		@ApiParam(value = "Country of organization to return", required = false)
    												@RequestParam(required=false) List<String> country,
                                               		@ApiParam(value = "Opportunities open in the organization", required = false)
    												@RequestParam(required=false) boolean open,
    												@ApiParam(value = "Status from organization", required = false)
													@RequestParam(required=false) String status,
													@ApiParam(value = "Category from organization", required = false)
													@RequestParam(required=false) String category
                                                  ) {
        return organizationService.findByCriteria(keyWord, country, open, status, category);
    }

    // TODO Define error codes: required input missing, etc 
    @CrossOrigin
    @RequestMapping(method = RequestMethod.POST)
    @ApiOperation(
    		value = "Create organization",
    		notes = "Creates an organization, and returns the organization created.",
    		response = OrganizationDTO.class)
    @ApiResponses(value = { 
    		@ApiResponse(code = 500, message = "Internal server error")})    
    public Map<String, Object> createOrganization(
    		@ApiParam(value = "Organization to create", required = true)
            @RequestBody @Valid CreateOrganizationDTO createOrganizationDTO) {
    	
        System.out.println("**************Create Organization: Begin**************");
        System.out.println("Organization Name = " + createOrganizationDTO.getName());
        Map<String, Object> responseData = null;
        //organizationDTO.setLogo(organizationService.getLogoUploadPath(organizationDTO.getId()));
        try {
            OrganizationDTO createdOrganization = organizationService.createOrganization(createOrganizationDTO);
            responseData = Collections.synchronizedMap(new HashMap<>());
            responseData.put("organization", createdOrganization);
        } catch (Exception e) {
            System.err.println(e);
        }
        
        System.out.println("**************Create Organization: End**************");
        return responseData;
    }

    @CrossOrigin
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    @ApiOperation(value = "Update an existing organization")
    public Map<String, Object> updateOrganization(@ApiParam(value = "Updated organization object", required = true)
                                                  @PathVariable("id") int id,
                                                  @RequestBody @Valid OrganizationDTO organizationDTO) {
        System.out.println("**************Update : id=" + organizationDTO.getId() + "**************");
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
    public void deleteOrganization(@ApiParam(value = "Organization id to delete", required = true)
                                   @PathVariable("id") int id) {
        System.out.println("************** Delete : id=" + id + "**************");

        try {
            organizationService.deleteOrganization(id);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @CrossOrigin
    @RequestMapping(value = "/{id}/logo", method = RequestMethod.GET)
    @ApiOperation(value = "Retrieves organization logo")
    public String retrieveOrganizationLogo(@ApiParam(value = "Organization id to get logo for", required = true)
                                           @PathVariable("id") int id) {
        File logo = new File(organizationService.getLogoUploadPath(id));
        try {
			FileInputStream fileInputStreamReader = new FileInputStream(logo);
            byte[] bytes = new byte[(int) logo.length()];
            fileInputStreamReader.read(bytes);
            fileInputStreamReader.close();
            return new String(Base64.encodeBase64(bytes));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

	@CrossOrigin
	@RequestMapping(value = "/user/{id}", method = RequestMethod.GET)
	@ApiOperation(value = "Find organizations by user id", notes = "Returns a collection of organizations")
	@ApiResponses(value = { @ApiResponse(code = 404, message = "ID of user invalid") })
	public List<OrganizationDTO> getOrganizationsByUser(
			@ApiParam(value = "userId of organizations to return", required = true) @PathVariable("id") Integer id) {

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
    @ApiResponses(value = {
            @ApiResponse(code = 404, message = "ID of organization or user invalid")
    })
    //TODO: Replace explicit user{id} with AuthN user id.
    public ResponseEntity<?> createUserOrganization(@ApiParam(value = "ID of user", required = true)
                                               @PathVariable("userId") Integer userId,
                                               @ApiParam(value = "ID of organization", required = true)
                                               @PathVariable("id") Integer organizationId) {
        try {
            organizationService.saveUserOrganization(userId, organizationId);
            URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                                                      .path("/{id}/users/{userId}")
                                                      .buildAndExpand(organizationId, userId).toUri();
            return ResponseEntity.created(location).build();
        } catch (NullPointerException | UserOrganizationException e) {
            throw new NotFoundException("ID of organization or user invalid, or relationship already exist");
        }
    }
}