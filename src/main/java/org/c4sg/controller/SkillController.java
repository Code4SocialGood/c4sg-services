package org.c4sg.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import org.c4sg.dto.SkillDTO;
import org.c4sg.dto.SkillUserCountDTO;
import org.c4sg.exception.BadRequestException;
import org.c4sg.exception.NotFoundException;
import org.c4sg.service.SkillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/skills")
@Api(description = "Operations about Skills", tags = "skill")
public class SkillController {

    @Autowired
    private SkillService skillService;

    @CrossOrigin
    @RequestMapping(produces = {"application/json"}, method = RequestMethod.GET)
    @ApiOperation(value = "Get all skills", notes = "Returns a collection of skills (alphabetical)")
    public List<SkillDTO> getSkills() {
        return skillService.findSkills();
    }
    
    @CrossOrigin
    @RequestMapping(value="/userCount",produces = {"application/json"}, method = RequestMethod.GET)
    @ApiOperation(value = "Get skills grouped by user count", notes = "Returns a collection of skills with user count")
    public List<SkillUserCountDTO> getSkillsWithUserCount() {
        return skillService.findSkillsWithUserCount();
    }
    
    @CrossOrigin
    @RequestMapping(value="/user",produces = {"application/json"}, method = RequestMethod.GET)
    @ApiOperation(value = "Get skills for an user by id", notes = "Returns a collection of skills for an user by id")
    public List<String> getSkillsForUser(@ApiParam(value = "ID of user to return", required = true)
    					 				 @RequestParam Integer id) {
        return skillService.findSkillsForUser(id);
    }
    
    @CrossOrigin
    @RequestMapping(value="/project",produces = {"application/json"}, method = RequestMethod.GET)
    @ApiOperation(value = "Get skills for a project by id", notes = "Returns a collection of skills for a project")
    public List<String> getSkillsForProject(@ApiParam(value = "ID of project to return", required = true)
    					    				@RequestParam Integer id) {
        return skillService.findSkillsForProject(id);
    }
    
    @CrossOrigin
    @RequestMapping(value="/user/skills", method = RequestMethod.PUT)
    @ApiOperation(value = "Add skills for a user", notes = "Adds skills for the user with display order")
    public void createSkillsForUser(@ApiParam(value = "ID of user to add skills",name="id", required = true)
							 	    @RequestParam Integer id,
				    				@ApiParam(value = "Skills in display order",name="skillsList", required = true)
								    @RequestParam List<String> skillsList) {
		try {
			skillService.saveSkillsForUser(id,skillsList);
		} catch (NullPointerException e) {
        	throw new NotFoundException(e.getMessage());
        } 
    }
    
    @CrossOrigin
    @RequestMapping(value="/project/skills", method = RequestMethod.PUT)
    @ApiOperation(value = "Add skills for a project", notes = "Adds skills for the project with display order")
    public void createSkillsForProject(@ApiParam(value = "ID of project to add skills", required = true)
							 	       @RequestParam Integer id,
				    				   @ApiParam(value = "Skills in display order", required = true)
								       @RequestParam List<String> skillsList) {
    	try {
			skillService.saveSkillsForProject(id,skillsList);
		} catch (NullPointerException e) {
            throw new NotFoundException(e.getMessage());
        } 
    }
}
