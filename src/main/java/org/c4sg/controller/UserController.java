package org.c4sg.controller;

import io.swagger.annotations.*;
import org.apache.tomcat.util.codec.binary.Base64;
import org.c4sg.dto.CreateUserDTO;
import org.c4sg.dto.UserDTO;
import org.c4sg.exception.NotFoundException;
import org.c4sg.service.UserService;
import org.c4sg.util.FileUploadUtil;
import org.c4sg.util.GeoCodeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import static org.c4sg.constant.Directory.AVATAR_UPLOAD;
import static org.c4sg.constant.Directory.RESUME_UPLOAD;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping(value = "/api/users")
@Api(description = "Operations about Users", tags = "user")
public class UserController {
    private static Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @CrossOrigin
    @RequestMapping(value = "/active", method = RequestMethod.GET)
    @ApiOperation(value = "Find active volunteer users", notes = "Returns a collection of active volunteer users")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "page", dataType = "integer", paramType = "query",
                value = "Results page you want to retrieve (0..N)"),
        @ApiImplicitParam(name = "size", dataType = "integer", paramType = "query",
                value = "Number of records per page."),
        @ApiImplicitParam(name = "sort", allowMultiple = true, dataType = "string", paramType = "query",
                value = "Sorting criteria in the format: property(,asc|desc). " +
                        "Default sort order is ascending. " +
                        "Multiple sort criteria are supported.")})
    public Page<UserDTO> getActiveUsers(Pageable pageable) {
        LOGGER.debug("**************active**************");
        return userService.findActiveVolunteers(pageable);
    }

    @RequestMapping(method = RequestMethod.GET)
    @ApiOperation(value = "Find all users", notes = "Returns a collection of users")
    public List<UserDTO> getUsers() {
        return userService.findAll();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ApiOperation(value = "Find user by ID", notes = "Returns a user")
    public UserDTO getUser(@ApiParam(value = "ID of user to return", required = true)
                           @PathVariable("id") int id) {
        return userService.findById(id);
    }
    
    @CrossOrigin
    @RequestMapping(value = "/email/{email}/", method = RequestMethod.GET)
    @ApiOperation(value = "Find user by email", notes = "Returns a user")
    public UserDTO getUserByEmail(@ApiParam(value = "email address", required = true)
                               @PathVariable("email") String email) {  	
    	return userService.findByEmail(email);
    }
    
    @RequestMapping(method = RequestMethod.POST)
    @ApiOperation(value = "Add a new user")
    public UserDTO createUser(@ApiParam(value = "User object to return", required = true)
                              @RequestBody CreateUserDTO createUserDTO) {
    	//calculate lat and long
    	try {
            UserDTO userDTO = userService.createUser(createUserDTO);
        	GeoCodeUtil geoCodeUtil = new GeoCodeUtil(userDTO);
        	Map<String, BigDecimal> geoCode = geoCodeUtil.getGeoCode();
			userDTO.setLatitude(geoCode.get("lat"));
		    userDTO.setLongitude(geoCode.get("lon"));
			
		} catch (Exception e) {
			
			throw new NotFoundException("Error getting geocode");
		}       
       
    	return userService.saveUser(userService);
    }

    @RequestMapping(method = RequestMethod.PUT)
    @ApiOperation(value = "Update an existing user")
    public UserDTO updateUser(@ApiParam(value = "Updated user object", required = true)
                              @RequestBody UserDTO userDTO) {
    	LOGGER.debug("**************updateUser**************");
    	LOGGER.debug("ID = " + userDTO.getId());
        return userService.saveUser(userDTO);
    }

    @CrossOrigin
    @RequestMapping(value = "/applicant/{id}", method = RequestMethod.GET)
    @ApiOperation(value = "Find applicants of a given project", notes = "Returns a collection of projects")
    @ApiResponses(value = {@ApiResponse(code = 404, message = "Applicants not found")})
    public ResponseEntity<List<UserDTO>> getApplicants(@ApiParam(value = "ID of project", required = true)
                                                       @PathVariable("id") Integer projectId) {
        List<UserDTO> applicants = userService.getApplicants(projectId);

        if (!applicants.isEmpty()) {
            return ResponseEntity.ok().body(applicants);
        } else {
            throw new NotFoundException("Applicants not found");
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ApiOperation(value = "Delete a user")
    public void deleteUser(@ApiParam(value = "User id to delete", required = true)
                           @PathVariable("id") int id) {
       LOGGER.debug("************** Delete : id=" + id + "**************");
        try {
            userService.deleteUser(id);
        } catch (Exception e) {        	
           LOGGER.error("Exception on delete user:", e);
        }
    }

    @RequestMapping(value = "/search", method = RequestMethod.GET)
    @ApiOperation(value = "Find a user by keyWord", notes = "Returns a collection of users")
    public List<UserDTO> search(@RequestParam(required = false) String keyWord,
                                @RequestParam(required = false) List<Integer> skills){
        return userService.search(keyWord,skills);
    }
    
    @RequestMapping(value = "/{id}/avatar", method = RequestMethod.POST)
	@ApiOperation(value = "Add new upload Avatar")
	public String uploadAvatar(@ApiParam(value = "user Id", required = true) @PathVariable("id") Integer id,
			@ApiParam(value = "Image File", required = true) @RequestPart("file") MultipartFile file) {

		String contentType = file.getContentType();
		if (!FileUploadUtil.isValidImageFile(contentType)) {
			return "Invalid image File! Content Type :-" + contentType;
		}
		File directory = new File(AVATAR_UPLOAD.getValue());
		if (!directory.exists()) {
			directory.mkdir();
		}
		File f = new File(userService.getAvatarUploadPath(id));
		try (FileOutputStream fos = new FileOutputStream(f)) {
			byte[] imageByte = file.getBytes();
			fos.write(imageByte);
			return "Success";
		} catch (Exception e) {
			return "Error saving avatar for User " + id + " : " + e;
		}
	}
    
    @CrossOrigin
    @RequestMapping(value = "/{id}/avatar", method = RequestMethod.GET)
    @ApiOperation(value = "Retrieves user avatar")
    public String retrieveAvatar(@ApiParam(value = "User id to get avatar for", required = true)
                                         @PathVariable("id") int id) {
        File avatar = new File(userService.getAvatarUploadPath(id));
        try {
			FileInputStream fileInputStreamReader = new FileInputStream(avatar);
            byte[] bytes = new byte[(int) avatar.length()];
            fileInputStreamReader.read(bytes);
            fileInputStreamReader.close();
            return new String(Base64.encodeBase64(bytes));
        } catch (IOException e) {
            e.printStackTrace();
        return null;
        }
    }

	@RequestMapping(value = "/{id}/resume", method = RequestMethod.POST)
	@ApiOperation(value = "Add new upload resume")
	public String uploadResume(@ApiParam(value = "user Id", required = true) @PathVariable("id") Integer id,
			@ApiParam(value = "Resume File(.pdf)", required = true) @RequestPart("file") MultipartFile file) {

		String contentType = file.getContentType();
		if (!FileUploadUtil.isValidResumeFile(contentType)) {
			return "Invalid pdf File! Content Type :-" + contentType;
		}
		File directory = new File(RESUME_UPLOAD.getValue());
		if (!directory.exists()) {
			directory.mkdir();
		}
		File f = new File(userService.getResumeUploadPath(id));
		try (FileOutputStream fos = new FileOutputStream(f)) {
			byte[] fileByte = file.getBytes();
			fos.write(fileByte);
			return "Success";
		} catch (Exception e) {
			return "Error saving resume for User " + id + " : " + e;
		}
	}
	
	
	
	@CrossOrigin
    @RequestMapping(value = "/{id}/resume", method = RequestMethod.GET)
    @ApiOperation(value = "Retrieves user resume")
	@ResponseBody
    public HttpEntity<byte[]> retrieveProjectImage(@ApiParam(value = "User id to get resume for", required = true)
                                         @PathVariable("id") int id) {
        File resume = new File(userService.getResumeUploadPath(id));
        try {
        	FileInputStream fileInputStreamReader = new FileInputStream(resume);
            byte[] bytes = new byte[(int) resume.length()];
            fileInputStreamReader.read(bytes);
            fileInputStreamReader.close();
            HttpHeaders header = new HttpHeaders();
            header.setContentType(MediaType.APPLICATION_PDF);
            header.setContentLength(bytes.length);

            return new HttpEntity<byte[]>(bytes, header);
            
        } catch (IOException e) {
            e.printStackTrace();
        return null;
        }
    }
}