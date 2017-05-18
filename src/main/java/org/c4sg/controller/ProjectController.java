package org.c4sg.controller;

import io.swagger.annotations.*;

import org.apache.tomcat.util.codec.binary.Base64;
import org.c4sg.dto.CreateProjectDTO;
import org.c4sg.dto.ProjectDTO;
import org.c4sg.exception.BadRequestException;
import org.c4sg.exception.NotFoundException;
import org.c4sg.exception.ProjectServiceException;
import org.c4sg.exception.UserProjectException;
import org.c4sg.service.ProjectService;
import org.c4sg.util.FileUploadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import javax.validation.constraints.Pattern;

import static org.c4sg.constant.Directory.PROJECT_UPLOAD;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/api/projects")
@Api(description = "Operations about Projects", tags = "project")
@Validated
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @CrossOrigin
    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Find all projects", notes = "Returns a collection of projects")
    public List<ProjectDTO> getProjects() {
        return projectService.findProjects();
    }

    @CrossOrigin
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ApiOperation(value = "Find project by ID", notes = "Returns a single project")
    public ProjectDTO getProject(@ApiParam(value = "ID of project to return", required = true)
                              @PathVariable("id") int id) {
        System.out.println("**************ID**************" + id);
        return projectService.findById(id);
    }

    @CrossOrigin
    @RequestMapping(value = "/organizations/{id}", method = RequestMethod.GET)
    @ApiOperation(value = "Find projects by Organization ID", notes = "Returns a list of projects")
    public List<ProjectDTO> getProjectsByOrganization(@ApiParam(value = "ID of an organization", required = true)
                                                   @PathVariable("id") int orgId) {
        System.out.println("**************OrganizationID**************" + orgId);
        return projectService.findByOrganization(orgId);
    }

    @CrossOrigin
    @RequestMapping(value = "/search", method = RequestMethod.GET)
    @ApiOperation(value = "Find ACTIVE project by keyWord or skills", notes = "Returns a collection of active projects")
    public List<ProjectDTO> getProjects(@ApiParam(value = "Keyword of project(s) to return")
                                        @RequestParam(required=false) String keyWord,
                                        @ApiParam(value = "Skills for projects to return")
                                        @RequestParam(required = false) List<Integer> skills,
                                        @ApiParam(value = "Status of the project")
    									@Pattern(regexp="[AC]")  @RequestParam(required = false) String status,
    									@ApiParam(value = "Location of the project")
    									@Pattern(regexp="[YN]") @RequestParam(required = false) String remote) {
        return projectService.findByKeyword(keyWord,skills,status,remote);
    }

    @CrossOrigin
    @RequestMapping(value = "/user", method = RequestMethod.GET)
    @ApiOperation(
    		value = "Find projects by user", 
    		notes = "Returns a list of projects searched by user ID and user-project status (applied/bookmarked). "
    				+ "If user-project status is not provided, returns all projects related to the user. "
    				+ "The projects are sorted in descending order of the timestamp they are bounded to the user.",
    		response =ProjectDTO.class , 
    		responseContainer = "List")
    @ApiResponses(value = { 
    		@ApiResponse(code = 404, message = "Missing required input")})  
    public List<ProjectDTO> getUserProjects(@ApiParam(value = "User ID", required = true)
    										@RequestParam Integer userId,
                                        	@ApiParam(value = "User project status, A-Applied, B-Bookmarked", allowableValues = "A, B")
    										@RequestParam (required = false) String userProjectStatus)	
                                        			throws ProjectServiceException {
    	System.out.println("************** Find Projects for User **************");
        return projectService.findByUser(userId, userProjectStatus);
    }

    @CrossOrigin
    @RequestMapping(method = RequestMethod.POST)
    @ApiOperation(value = "Add a new project")
    public Map<String, Object> createProject(@ApiParam(value = "Project object to return", required = true)
                                             @RequestBody @Valid CreateProjectDTO createProjectDTO) {

        System.out.println("************** Add **************");

        Map<String, Object> responseData = null;
        try {
            ProjectDTO createProject = projectService.createProject(createProjectDTO);
            responseData = Collections.synchronizedMap(new HashMap<>());
            responseData.put("project", createProject);
        } catch (Exception e) {
            System.out.println(e);
        }

        return responseData;
    }

    @CrossOrigin
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ApiOperation(value = "Deletes a project")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProject(@ApiParam(value = "Project id to delete", required = true)
                              @PathVariable("id") int id) {

        System.out.println("************** Delete : id=" + id + "**************");

        try {
            projectService.deleteProject(id);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @CrossOrigin
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    @ApiOperation(value = "Update an existing project")
    public Map<String, Object> updateProject(@ApiParam(value = "Updated project object", required = true)
                                             @RequestBody @Valid ProjectDTO project) {

        System.out.println("**************Update : id=" + project.getId() + "**************");

        Map<String, Object> responseData = null;

        try {
            ProjectDTO updateProject = projectService.updateProject(project);
            responseData = Collections.synchronizedMap(new HashMap<>());
            responseData.put("project", updateProject);
        } catch (Exception e) {
            System.out.println(e);
        }

        return responseData;
    }

    @CrossOrigin
    @RequestMapping(value = "/{id}/users/{userId}", method = RequestMethod.POST)
    @ApiOperation(value = "Create a relation between user and project")
    @ApiResponses(value = {
            @ApiResponse(code = 404, message = "ID of project or user invalid")
    })
    //TODO: Replace explicit user{id} with AuthN user id.
    public ResponseEntity<?> createUserProject(@ApiParam(value = "ID of user", required = true)
                                               @PathVariable("userId") Integer userId,
                                               @ApiParam(value = "ID of project", required = true)
                                               @PathVariable("id") Integer projectId) {
        try {
            projectService.saveUserProject(userId, projectId);
            URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                                                      .path("/{id}/users/{userId}")
                                                      .buildAndExpand(projectId, userId).toUri();
            return ResponseEntity.created(location).build();
        } catch (NullPointerException | UserProjectException e) {
            throw new NotFoundException("ID of project or user invalid");
        }
    }

    @RequestMapping(value = "/{id}/image", method = RequestMethod.POST)
   	@ApiOperation(value = "Add new image file for project")
   	public String uploadImage(@ApiParam(value = "user Id", required = true) @PathVariable("id") Integer id,
   			@ApiParam(value = "Image File", required = true) @RequestPart("file") MultipartFile file) {

   		String contentType = file.getContentType();
   		if (!FileUploadUtil.isValidImageFile(contentType)) {
   			return "Invalid image File! Content Type :-" + contentType;
   		}
   		File directory = new File(PROJECT_UPLOAD.getValue());
   		if (!directory.exists()) {
   			directory.mkdir();
   		}
   		File f = new File(projectService.getImageUploadPath(id));
   		try (FileOutputStream fos = new FileOutputStream(f)) {
   			byte[] imageByte = file.getBytes();
   			fos.write(imageByte);
   			return "Success";
   		} catch (Exception e) {
   			return "Error saving image for Project " + id + " : " + e;
   		}
   	}

    @CrossOrigin
    @RequestMapping(value = "/{id}/image", method = RequestMethod.GET)
    @ApiOperation(value = "Retrieves project image")
    public String retrieveImage(@ApiParam(value = "Project id to get image for", required = true)
                                         @PathVariable("id") int id) {
        File image = new File(projectService.getImageUploadPath(id));
        try {
			FileInputStream fileInputStreamReader = new FileInputStream(image);
            byte[] bytes = new byte[(int) image.length()];
            fileInputStreamReader.read(bytes);
            fileInputStreamReader.close();
            return new String(Base64.encodeBase64(bytes));
        } catch (IOException e) {
            e.printStackTrace();
        return null;
        }
    }

    @CrossOrigin
    @RequestMapping(value = "/{id}/image", method = RequestMethod.DELETE)
    @ApiOperation(value = "Deletes a project's images")
    public String deleteImage(@ApiParam(value = "Project id to delete image for", required = true)
    							@PathVariable("id") int id) {
    	ProjectDTO p = projectService.findById(id);
    	File image = new File(projectService.getImageUploadPath(id));
    	try {
    		boolean del = image.delete();
    		p.setImageUrl(null);
    		projectService.updateProject(p);
    		if (del) {
    			return "Success";
    		} else {
    			return "Fail";
    		}
    	} catch (Exception e) {
    		System.out.println(e);
    		return "Error";
    	}
    }
    
    
    
    @CrossOrigin
    @RequestMapping(value = "/bookmark/projects/{projectId}/users/{userId}", method = RequestMethod.POST)
    @ApiOperation(value = "Create a bookmark for a project")
    @ApiResponses(value = {
            @ApiResponse(code = 404, message = "Invalid project or user")
    })
    //TODO: Replace explicit user{id} with AuthN user id.
    public ResponseEntity<?> createUserProjectBookmark(@ApiParam(value = "Project ID", required = true)
                                               @PathVariable("projectId") Integer projectId,
                                               @ApiParam(value = "User ID", required = true)
                                               @PathVariable("userId") Integer userId) {
        try {
            projectService.saveUserProjectBookmark(userId, projectId);
            URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                                                      .path("/bookmark/projects/{projectId}/users/{userId}")
                                                      .buildAndExpand(projectId, userId).toUri();
            return ResponseEntity.created(location).build();
        } catch (NullPointerException e) {
            throw new NotFoundException(e.getMessage());
        }
        catch(UserProjectException e){
        	throw new BadRequestException(e.getErrorMessage());
        }
    }
    
}

