package org.c4sg.controller;

import org.c4sg.C4SgApplication;
import org.c4sg.TestDataSet;
import org.c4sg.dto.StoryDTO;
import org.c4sg.mapper.StoryMapper;
import org.c4sg.service.StoryService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
public class StoryControllerTest {

    @Mock
    private StoryService storyService;

    @InjectMocks
    StoryController cut = new StoryController();

    @Test
    public void getStories()  {

        List<StoryDTO> stories = new ArrayList<>();
        when(storyService.findStories()).thenReturn(stories);

        assertEquals(stories, cut.getStories());
    }

}