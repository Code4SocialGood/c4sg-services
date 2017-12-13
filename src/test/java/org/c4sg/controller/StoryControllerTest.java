package org.c4sg.controller;

import org.c4sg.C4SgApplication;
import org.c4sg.TestDataSet;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * An integration test, that starts the C4SgApplication application and loads the actual spring context. These tests will hit the underlying database.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {C4SgApplication.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class StoryControllerTest {

    private static final String URI_GET_PROJECTS = "/api/stories";

    @Autowired
    private StoryController storyController;

    private MockMvc mockMvc;

    @Before
    public void setup() throws Exception {
        this.mockMvc = MockMvcBuilders.standaloneSetup(storyController).build();
    }

    @Test
    public void getStories() throws Exception {

        this.mockMvc.perform(
                get(URI_GET_PROJECTS).accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(greaterThan(1))))
                .andExpect(jsonPath("$[1].title", is(TestDataSet.STORY_1_TITLE)))
                .andExpect(jsonPath("$[0].title", is(TestDataSet.STORY_2_TITLE)));
    }

}