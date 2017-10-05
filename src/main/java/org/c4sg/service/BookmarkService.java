package org.c4sg.service;

import java.util.List;

import org.c4sg.dto.BookmarkDTO;
import org.c4sg.dto.ProjectDTO;

public interface BookmarkService {

	BookmarkDTO createBookmark(Integer userId, Integer projectId);
	
	List<ProjectDTO> getBookmarkByUser(Integer userId);
}
