package org.c4sg.controller;

import org.c4sg.dto.CreateStoryDTO;
import org.c4sg.dto.StoryDTO;
import org.c4sg.exception.StoryServiceException;
import org.c4sg.service.StoryService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

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

    @Test
    public void createStory()  {
        CreateStoryDTO dto = Mockito.mock(CreateStoryDTO.class);
        StoryDTO story = Mockito.mock(StoryDTO.class);
        when(storyService.createStory(dto)).thenReturn(story);
        assertEquals(story, cut.createStory(dto));
    }

    @Test(expected = StoryServiceException.class)
    public void createStory_ServiceException()  {
        CreateStoryDTO dto = Mockito.mock(CreateStoryDTO.class);
        when(storyService.createStory(dto)).thenThrow(new RuntimeException());
        cut.createStory(dto);
    }
}