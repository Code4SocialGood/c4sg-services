package org.c4sg.controller;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.c4sg.dto.StoryDTO;
import org.c4sg.service.StoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
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
}
