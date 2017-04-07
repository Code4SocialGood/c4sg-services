package org.c4sg.controller;

import static org.c4sg.constant.Directory.LOGO_UPLOAD;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.validation.Valid;
import org.apache.tomcat.util.codec.binary.Base64;
import org.c4sg.dto.CreateOrganizationDTO;
import org.c4sg.dto.OrganizationDTO;
import org.c4sg.entity.Organization;
import org.c4sg.exception.NotFoundException;
import org.c4sg.service.OrganizationService;
import org.c4sg.util.FileUploadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import javax.validation.Valid;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.c4sg.constant.Directory.LOGO_UPLOAD;

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
    @ApiOperation(value = "Find organization by keyWord", notes = "Searches the keyword in organization name and description, case insensitive. The search result is sorted by project update time in descending order.")
    public List<OrganizationDTO> getOrganizations(@ApiParam(value = "Name or description of organization to return", required = true)
                                                  @RequestParam String keyWord) {
        return organizationService.findByKeyword(keyWord);
    }

    @CrossOrigin
    @RequestMapping(method = RequestMethod.POST)
    @ApiOperation(value = "Add a new organization")
    public Map<String, Object> createOrganization(@ApiParam(value = "Organization object to return", required = true)
                                                  @RequestBody @Valid CreateOrganizationDTO createOrganizationDTO) {
        System.out.println("**************Create**************");
        Map<String, Object> responseData = null;
        //organizationDTO.setLogo(organizationService.getLogoUploadPath(organizationDTO.getId()));
        try {
            Organization createdOrganization = organizationService.createOrganization(createOrganizationDTO);
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
}