package org.c4sg.dto;

import java.util.Date;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class BookmarkDTO {
	
	@NotNull(message = "Project Id is required")
	private Integer projectId;
	
	@NotNull(message = "User Id is required")
	private Integer userId;
	
	public Integer getProjectId(){
		return projectId;
	}
	
	public void setProjectId(Integer projectId){
		this.projectId = projectId;
	}
	
	public Integer getUserId(){
		return userId;
	}
	
	public void setUserId(Integer userId){
		this.userId = userId;
	}

}
