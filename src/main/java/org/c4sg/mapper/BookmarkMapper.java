package org.c4sg.mapper;


import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


import org.c4sg.dto.BookmarkDTO;
import org.c4sg.dto.ProjectDTO;
import org.c4sg.entity.Bookmark;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Component;

@Component
public class BookmarkMapper extends ModelMapper {
	
	public BookmarkDTO getBookmarkDtoFromEntity(Bookmark bookmark){
		BookmarkDTO bookmarkDTO = map(bookmark, BookmarkDTO.class);
		return bookmarkDTO;
	}
	
	public Bookmark getBookmarkEntityFromDto(BookmarkDTO bookmarkDTO){
		Bookmark bookmark = map(bookmarkDTO, Bookmark.class);
		return bookmark;
	}
	
	public List<BookmarkDTO> getBookmarkDtosFromEntities(List<Bookmark> bookmarks){
		List<BookmarkDTO> bookmarkList = new ArrayList<BookmarkDTO>();
		Iterator<Bookmark> bookmarkIter = bookmarks.iterator();
		while (bookmarkIter.hasNext()) {
			Bookmark bookmark = bookmarkIter.next();
			bookmarkList.add(getBookmarkDtoFromEntity(bookmark));
		}
		return bookmarkList;
	}
	
	public ProjectDTO getProjectDtoFromEntity(Bookmark bookmark){
		Type projectTypeDTO = new TypeToken<ProjectDTO>() {}.getType();
		ProjectDTO projectDTO = map(bookmark.getProject(), projectTypeDTO);
		return projectDTO;
	}
	
	public List<ProjectDTO> getProjectDtosFromBookmarkEntities(List<Bookmark> bookmarks){
		List<ProjectDTO> projectList = new ArrayList<ProjectDTO>();
		Iterator<Bookmark> bookmarkIter = bookmarks.iterator();
		while (bookmarkIter.hasNext()) {
			Bookmark bookmark = bookmarkIter.next();
			projectList.add(getProjectDtoFromEntity(bookmark));
		}
		return projectList;
	}

}
