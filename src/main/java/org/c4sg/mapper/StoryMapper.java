package org.c4sg.mapper;

import org.c4sg.dto.StoryDTO;
import org.c4sg.entity.Story;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Component
public class StoryMapper extends ModelMapper {

    private StoryDTO getStoryDtoFromEntity(Story story) {
        getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        StoryDTO storyDTO = map(story, StoryDTO.class);
        return storyDTO;
    }

    public List<StoryDTO> getDtosFromEntities(List<Story> stories) {

        List<StoryDTO> storyDTOS = new ArrayList<>();

        if (stories != null) {

            Iterator<Story> storyIterator = stories.iterator();
            while (storyIterator.hasNext()) {
                Story story = storyIterator.next();
                storyDTOS.add(getStoryDtoFromEntity(story));
            }
        }
        return storyDTOS;
    }
}
