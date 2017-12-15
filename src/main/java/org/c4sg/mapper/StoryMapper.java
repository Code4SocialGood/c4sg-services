package org.c4sg.mapper;

import org.c4sg.dto.StoryDTO;
import org.c4sg.entity.Story;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class StoryMapper extends ModelMapper {

    private StoryDTO getStoryDtoFromEntity(Story story) {
        getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        StoryDTO storyDTO = map(story, StoryDTO.class);
        return storyDTO;
    }

    public List<StoryDTO> getDtosFromEntities(List<Story> entities) {
        if (entities == null)
            return new ArrayList<>();
        return entities.stream().map(entity -> getStoryDtoFromEntity(entity)).collect(Collectors.toList());
    }
}
