package org.c4sg.controller;

import io.swagger.annotations.*;

import org.c4sg.dto.ApplicantDTO;
import org.c4sg.dto.CreateUserDTO;
import org.c4sg.dto.UserDTO;
import org.c4sg.exception.UserServiceException;
import org.c4sg.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import javax.validation.constraints.Pattern;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping(value = "/api/users")
@Api(description = "Operations about Users", tags = "user")
public class UserController {
    private static Logger LOGGER = LoggerFactory.getLogger(UserController.class);
    
    @Autowired
    private UserService userService;
       
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

    @RequestMapping(value = "/organization/{orgId}", method = RequestMethod.GET)
    @ApiOperation(value = "Find users by Organization ID", notes = "Returns a list of users from this organization")
    public List<UserDTO> getUsersInOrganization(@ApiParam(value = "ID of organization to return users", required = true)
                           @PathVariable("orgId") int orgId) {
        return userService.findByOrgId(orgId);
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

        try {
            return userService.createUser(createUserDTO);
        } catch (Exception e) {
            throw new UserServiceException("Error creating user entity: " + e.getCause().getMessage());
        }
    }
    
    @RequestMapping(method = RequestMethod.PUT)
    @ApiOperation(value = "Update an existing user")
    public UserDTO updateUser(@ApiParam(value = "Updated user object", required = true)
                              @RequestBody UserDTO userDTO) {
        try {
        	return userService.saveUser(userDTO);
        } catch (Exception e) {
            throw new UserServiceException("Error creating user entity: " + e.getCause().getMessage());
        }
    }
    
    @CrossOrigin
    @RequestMapping(value = "/applicant/{id}", method = RequestMethod.GET)
    @ApiOperation(value = "Find applicants of a given project", notes = "Returns a collection of users")
    public List<ApplicantDTO> getApplicants(@ApiParam(value = "ID of project", required = true)
                                                       @PathVariable("id") Integer projectId) {
        return userService.getApplicants(projectId);
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
    @ApiOperation(value = "Find a user by keyWord, skills, status, role or publicFlag", notes = "Returns a collection of users")
    public Page<UserDTO> getUsers(@ApiParam(value = "Keyword like name , title, introduction, state, country")
                                        @RequestParam(required=false) String keyWord,
                                        @ApiParam(value = "Skills of the User")
                                        @RequestParam(required = false) List<Integer> skills,
                                        @ApiParam(value = "Status of the User")
    									@Pattern(regexp="[AD]")  @RequestParam(required = false) String status,
    									@ApiParam(value = "User Role")
    									@Pattern(regexp="[VOA]") @RequestParam(required = false) String role,
									    @ApiParam(value = "User Public Flag")
										@Pattern(regexp="[YN]") @RequestParam(required = false) String publishFlag,
    @ApiParam(value = "Results page you want to retrieve (0..N)", required=false)
    @RequestParam(required=false) Integer page,
    @ApiParam(value = "Number of records per page", required=false)
    @RequestParam(required=false) Integer size)
    {
        return userService.search(keyWord,skills,status,role,publishFlag,page,size);
    }
        
    @CrossOrigin
    @RequestMapping(value = "/{id}/avatar", params = "imgUrl", method = RequestMethod.PUT)
	@ApiOperation(value = "Upload a user avatar image")
	public void saveAvatar(@ApiParam(value = "user Id", required = true) @PathVariable("id") Integer id,
			@ApiParam(value = "Image Url", required = true) @RequestParam("imgUrl") String url) {

    	userService.saveAvatar(id, url);
	}
    
    /* Obsolete, replaced with search endpoint  
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
    public Page<UserDTO> getActiveUsers(@ApiIgnore Pageable pageable) {
        LOGGER.debug("**************active**************");
        return userService.findActiveVolunteers(pageable);
    }
    */
}
