package org.c4sg.service.impl;

import static java.util.Objects.requireNonNull;

import java.util.List;

import org.c4sg.dao.BookmarkDAO;
import org.c4sg.dao.ProjectDAO;
import org.c4sg.dao.UserDAO;
import org.c4sg.dto.BookmarkDTO;
import org.c4sg.dto.ProjectDTO;
import org.c4sg.entity.Bookmark;
import org.c4sg.entity.Project;
import org.c4sg.entity.User;
import org.c4sg.exception.ProjectServiceException;
import org.c4sg.exception.UserProjectException;
import org.c4sg.mapper.BookmarkMapper;
import org.c4sg.mapper.ProjectMapper;
import org.c4sg.service.BookmarkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookmarkServiceImpl implements BookmarkService {
	
	@Autowired
    private BookmarkDAO bookmarkDAO;
	
	@Autowired
    private BookmarkMapper bookmarkMapper;	
	
	@Autowired
    private UserDAO userDAO;
	
	@Autowired
    private ProjectDAO projectDAO;
	
	@Override
    public List<ProjectDTO> getBookmarkByUser(Integer userId) throws ProjectServiceException {
    	
    	List<Bookmark> bookmarks = bookmarkDAO.findByUser_Id(userId);
    	return bookmarkMapper.getProjectDtosFromBookmarkEntities(bookmarks);    	
    }
	
	@Override
    public BookmarkDTO createBookmark(Integer userId, Integer projectId){
    	
    	User user = userDAO.findById(userId);
        requireNonNull(user, "Invalid User Id");
        Project project = projectDAO.findById(projectId);
        requireNonNull(project, "Invalid Project Id");
       
        isBookmarked(userId, projectId);
	    Bookmark bookmark = new Bookmark();
	    bookmark.setUser(user);
	    bookmark.setProject(project);	        
	    bookmarkDAO.save(bookmark);
	        
        return bookmarkMapper.getBookmarkDtoFromEntity(bookmark);
    	
    } 
	
	private void isBookmarked(Integer userId, Integer projectId) throws UserProjectException {

    	Bookmark bookmark = bookmarkDAO.findByUser_IdAndProject_Id(userId, projectId);
    	
    	if(java.util.Objects.nonNull(bookmark) && bookmark.getUser().getId().equals(userId) && bookmark.getProject().getId().equals(projectId))
        {
    		throw new UserProjectException("Record already exist");
        }
    	    	
    }

}
