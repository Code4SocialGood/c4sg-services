package org.c4sg.service;

import org.c4sg.dto.StoryDTO;

import java.util.List;

public interface StoryService {

	List<StoryDTO> findStories();

}
