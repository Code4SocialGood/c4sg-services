package org.c4sg.controller;

import org.c4sg.dto.StoryDTO;
import org.c4sg.service.StoryService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

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