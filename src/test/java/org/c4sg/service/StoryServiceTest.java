package org.c4sg.service;

import org.c4sg.dao.StoryDAO;
import org.c4sg.dto.StoryDTO;
import org.c4sg.entity.Story;
import org.c4sg.mapper.StoryMapper;
import org.c4sg.service.impl.StoryServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class StoryServiceTest {

    @Mock
    private StoryDAO storyDAO;

    @Mock
    private StoryMapper storyMapper;

    @InjectMocks
    StoryService cut = new StoryServiceImpl();

    @Before
    public void setup() {
        assertNotNull(cut);
    }

    @Test
    public void findStories() {

        // Setup
        StoryDTO story1_dto = mock(StoryDTO.class);
        StoryDTO story2_dto = mock(StoryDTO.class);
        List<Story> stories = asList(mock(Story.class), mock(Story.class));
        List<StoryDTO> storyDTOS = asList(story1_dto, story2_dto);

        when(storyDAO.findAllByOrderByIdDesc()).thenReturn(stories);
        when(storyMapper.getDtosFromEntities(stories)).thenReturn(storyDTOS);

        // execute
        List<StoryDTO> result = cut.findStories();

        // verify
        assertTrue(result.contains(story1_dto));
        assertTrue(result.contains(story2_dto));
    }

    @Test
    public void findStories_none_exist() {

        // Setup
        when(storyDAO.findAllByOrderByIdDesc()).thenReturn(null);
        when(storyMapper.getDtosFromEntities(null)).thenReturn(new ArrayList<>());

        // execute
        List<StoryDTO> result = cut.findStories();

        // verify
        assertTrue(result.isEmpty());
    }
}