package org.c4sg.controller;

import io.swagger.annotations.*;

import org.c4sg.dto.ApplicantDTO;
import org.c4sg.dto.CreateUserDTO;
import org.c4sg.dto.JobTitleDTO;
import org.c4sg.dto.UserDTO;
import org.c4sg.exception.UserServiceException;
import org.c4sg.service.ProjectService;
import org.c4sg.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import javax.validation.constraints.Pattern;

@RestController
@RequestMapping(value = "/api/users")
@Api(description = "Operations about Users", tags = "user")
public class UserController {
    private static Logger LOGGER = LoggerFactory.getLogger(UserController.class);
    
    @Autowired
    private ProjectService projectService;
    
    @Autowired
    private UserService userService;
    
	@CrossOrigin
    @RequestMapping(method = RequestMethod.GET)
    @ApiOperation(value = "Find all users", notes = "Returns a collection of users")
    public List<UserDTO> getUsers() {
		
    	System.out.println("************** UserController.getUsers() **************");
    	
        return userService.findAll();
    }
    
	@CrossOrigin
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ApiOperation(value = "Find user by ID", notes = "Returns a user")
    public UserDTO getUser(
    		@ApiParam(value = "ID of user to return", required = true) @PathVariable("id") int id) {
		
    	System.out.println("************** UserController.getUser()" 
                + ": id=" + id  
                + " **************");
    	
        return userService.findById(id);
    }

	@CrossOrigin
    @RequestMapping(value = "/organization/{orgId}", method = RequestMethod.GET)
    @ApiOperation(value = "Find users by Organization ID", notes = "Returns a list of users from this organization")
    public List<UserDTO> getUsersInOrganization(
    		@ApiParam(value = "ID of organization to return users", required = true) @PathVariable("orgId") int orgId) {
		
    	System.out.println("************** UserController.getUsersInOrganization()" 
                + ": orgId=" + orgId  
                + " **************");
    	
        return userService.findByOrgId(orgId);
    }
    
    @CrossOrigin
    @RequestMapping(value = "/email/{email}/", method = RequestMethod.GET)
    @ApiOperation(value = "Find user by email", notes = "Returns a user")
    public UserDTO getUserByEmail(
    		@ApiParam(value = "email address", required = true) @PathVariable("email") String email) {
    	

    	System.out.println("************** UserController.getUserByEmail()"
                + ": email=" + email  
                + " **************");
    	
        return userService.findByEmail(email);
    }
    
	@CrossOrigin
    @RequestMapping(method = RequestMethod.POST)
    @ApiOperation(value = "Add a new user")
    public UserDTO createUser(
    		@ApiParam(value = "User object to return", required = true) @RequestBody CreateUserDTO createUserDTO) {

    	System.out.println("************** UserController.createUser()" 
                + ": createUserDTO=" + createUserDTO  
                + " **************");
    	
        try {
            return userService.createUser(createUserDTO);
        } catch (Exception e) {
            throw new UserServiceException("Error creating user entity: " + e.getCause().getMessage());
        }
    }
    
	@CrossOrigin
    @RequestMapping(method = RequestMethod.PUT)
    @ApiOperation(value = "Update an existing user")
    public UserDTO updateUser(
    		@ApiParam(value = "Updated user object", required = true) @RequestBody UserDTO userDTO) {
		
    	System.out.println("************** UserController.updateUser()" 
                + ": userDTO=" + userDTO  
                + " **************");
    	
        try {
        	return userService.saveUser(userDTO);
        } catch (Exception e) {
            throw new UserServiceException("Error creating user entity: " + e.getCause().getMessage());
        }
    }   
    
	@CrossOrigin
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ApiOperation(value = "Delete a user")
    public void deleteUser(
    		@ApiParam(value = "User id to delete", required = true) @PathVariable("id") int id) {

    	System.out.println("************** UserController.deleteUser()" 
                + ": id=" + id  
                + " **************");
    	
        try {
            userService.deleteUser(id);
        } catch (Exception e) {
        	LOGGER.error("Exception on delete user:", e);
        	throw new UserServiceException("Error deleting user");
            
        }
    }
    
	@CrossOrigin
    @RequestMapping(value = "/search", method = RequestMethod.GET)
    @ApiOperation(value = "Find a user by keyWord, skills, status, role or publicFlag", notes = "Returns a collection of users")
    public Page<UserDTO> getUsers(
    		@ApiParam(value = "Keyword like name , title, introduction, state, country") @RequestParam(required=false) String keyWord,
            @ApiParam(value = "Job Titles of the user")	@RequestParam(required = false)  List<Integer> jobTitles,
            @ApiParam(value = "Skills of the User") @RequestParam(required = false) List<Integer> skills,
            @ApiParam(value = "Status of the User") @Pattern(regexp="[AD]")  @RequestParam(required = false) String status,
    		@ApiParam(value = "User Role") @Pattern(regexp="[VOA]") @RequestParam(required = false) String role,
			@ApiParam(value = "User Public Flag") @Pattern(regexp="[YN]") @RequestParam(required = false) String publishFlag,
			@ApiParam(value = "Results page you want to retrieve (0..N)", required=false) @RequestParam(required=false) Integer page,
    		@ApiParam(value = "Number of records per page", required=false) @RequestParam(required=false) Integer size) {
		
    	System.out.println("************** UserController.getUsers()" 
                + ": keyWord=" + keyWord  
                + "; jobTitles=" + jobTitles  
                + "; skills=" + skills  
                + "; status=" + status  
                + "; role=" + role  
                + "; publishFlag=" + publishFlag  
                + "; page=" + page  
                + "; size=" + size                  
                + " **************");
    	
        return userService.search(keyWord, jobTitles, skills, status, role, publishFlag, page, size);
    }
        
    @CrossOrigin
    @RequestMapping(value = "/{id}/avatar", params = "imgUrl", method = RequestMethod.PUT)
	@ApiOperation(value = "Upload a user avatar image")
	public void saveAvatar(
			@ApiParam(value = "user Id", required = true) @PathVariable("id") Integer id,
			@ApiParam(value = "Image Url", required = true) @RequestParam("imgUrl") String url) {

    	System.out.println("************** UserController.saveAvatar()" 
                + ": id=" + id  
                + "; url=" + url                  
                + " **************");
    	
    	userService.saveAvatar(id, url);
	}
    
    @CrossOrigin
    @RequestMapping(value="/jobTitles", method = RequestMethod.GET)
    @ApiOperation(value = "Get a list of job titles")
    public List<JobTitleDTO> getJobTitles() {
    	
    	System.out.println("************** UserController.getJobTitles() **************");
    	
        return projectService.findJobTitles();

    }

}