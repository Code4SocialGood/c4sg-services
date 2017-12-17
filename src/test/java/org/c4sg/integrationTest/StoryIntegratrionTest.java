package org.c4sg.integrationTest;

import org.c4sg.C4SGTest;
import org.c4sg.C4SgApplication;
import org.c4sg.controller.StoryController;
import org.c4sg.dto.CreateStoryDTO;
import org.c4sg.dto.StoryDTO;
import org.c4sg.entity.StoryType;
import org.c4sg.service.StoryService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.comparesEqualTo;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * An integration test, that starts the C4SgApplication application and loads the actual spring context. These tests will hit the underlying database.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {C4SgApplication.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class StoryIntegratrionTest extends C4SGTest {

    private static final String URI_GET_STORIES = "/api/stories";

    @Autowired
    private StoryController storyController;

    @Autowired
    private StoryService storyService;

    private MockMvc mockMvc;

    @Before
    public void setup() throws Exception {
        this.mockMvc = MockMvcBuilders.standaloneSetup(storyController).build();
    }

    @Test
    public void createAndGetStory() throws Exception {

        // Pre-condition
        int existingStories = storyService.findStories().size();

        CreateStoryDTO request = buildValidCreateStoryDTO();
        this.mockMvc.perform(
                post(URI_GET_STORIES, request)
                .content(asJsonString(request))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        this.mockMvc.perform(
                get(URI_GET_STORIES).accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(comparesEqualTo(existingStories + 1))));
    }

    private CreateStoryDTO buildValidCreateStoryDTO() {

        CreateStoryDTO request = new CreateStoryDTO();
        String title = "an open source story";
        String imageUrl = "image url";
        String body = "body";

        request.setTitle(title);
        request.setType(StoryType.ORGANIZATION);
        request.setUserId(1);
        request.setBody(body);
        request.setImageUrl(imageUrl);

        return request;
    }
}