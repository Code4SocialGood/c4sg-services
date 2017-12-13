package org.c4sg.service.impl;


import org.c4sg.dao.StoryDAO;
import org.c4sg.dto.StoryDTO;
import org.c4sg.entity.Story;
import org.c4sg.mapper.StoryMapper;
import org.c4sg.service.StoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StoryServiceImpl implements StoryService {
	
    @Autowired
    private StoryDAO storyDAO;

    @Autowired
    private StoryMapper storyMapper;

    public List<StoryDTO> findStories() {
        List<Story> stories = storyDAO.findAllByOrderByIdDesc();
        return storyMapper.getDtosFromEntities(stories);
    }
}
