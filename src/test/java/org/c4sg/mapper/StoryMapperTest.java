package org.c4sg.mapper;

import org.c4sg.dto.StoryDTO;
import org.c4sg.entity.Story;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class StoryMapperTest {

    StoryMapper mapper = new StoryMapper();

    @Test
    public void getDtosFromEntities() {

        Integer id1 = 998;
        Integer id2 = 999;
        Story story1 = mock(Story.class);
        when(story1.getId()).thenReturn(id1);
        Story story2 = mock(Story.class);
        when(story2.getId()).thenReturn(id2);

        List<Story> stories = Arrays.asList(story1, story2);

        List<StoryDTO> result = mapper.getDtosFromEntities(stories);

        assertNotNull(result.stream().filter(x -> x.getId() == id1).findAny().get());
        assertNotNull(result.stream().filter(x -> x.getId() == id2).findAny().get());
    }

    @Test
    public void getDtosFromEntities_NullEntities() {
        assertTrue(mapper.getDtosFromEntities(null).isEmpty());
    }

    @Test
    public void getDtosFromEntities_EmptyEntities() {
        assertTrue(mapper.getDtosFromEntities(new ArrayList<>()).isEmpty());
    }
}