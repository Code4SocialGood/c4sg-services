package org.c4sg.service.impl;


import org.c4sg.dao.StoryDAO;
import org.c4sg.dao.UserDAO;
import org.c4sg.dto.CreateStoryDTO;
import org.c4sg.dto.StoryDTO;
import org.c4sg.entity.User;
import org.c4sg.entity.Story;
import org.c4sg.mapper.StoryMapper;
import org.c4sg.service.StoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.Objects.requireNonNull;

@Service
public class StoryServiceImpl implements StoryService {
	
    @Autowired
    private StoryDAO storyDAO;

    @Autowired
    private StoryMapper storyMapper;

    @Autowired
    private UserDAO userDAO;

    public List<StoryDTO> findStories() {
        List<Story> stories = storyDAO.findAllByOrderByIdDesc();
        return storyMapper.getDtosFromEntities(stories);
    }

    @Override
    public StoryDTO createStory(CreateStoryDTO createStoryDTO) {
        Story story = storyMapper.getStoryEntityFromCreateStoryDto(createStoryDTO);

        User user = userDAO.findById(createStoryDTO.getUserId());
        requireNonNull(user, "Invalid User Id");
        story.setUser(user);

        Story storyEntity = storyDAO.save(story);
        return storyMapper.getStoryDtoFromEntity(storyEntity);
    }
}
