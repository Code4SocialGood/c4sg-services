package org.c4sg.controller;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.c4sg.dto.CreateStoryDTO;
import org.c4sg.dto.StoryDTO;
import org.c4sg.exception.StoryServiceException;
import org.c4sg.service.StoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(value = "/api/stories")
@Api(description = "Operations about stories", tags = "story")
public class StoryController {

    @Autowired
    private StoryService storyService;

    @CrossOrigin
    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Find all stories", notes = "Returns a collection of stories")
    public List<StoryDTO> getStories() {
        return storyService.findStories();
    }

    @CrossOrigin
    @RequestMapping(method = RequestMethod.POST)
    @ApiOperation(value = "Add a new story")
    public StoryDTO createStory(
            @ApiParam(value = "Story object to return", required = true) @RequestBody CreateStoryDTO createStoryDTO) {

        try {
            return storyService.createStory(createStoryDTO);
        } catch (Exception e) {
            throw new StoryServiceException("Error creating story entity: " + e.getCause().getMessage());
        }
    }
}
