package org.c4sg.dto;

import java.util.Date;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class ApplicationDTO {
	
	@NotNull(message = "Project Id is required")
	private Integer projectId;
	
	@NotNull(message = "User Id is required")
	private Integer userId;
	
	@NotNull(message = "Application status is required")
	private String status;
	
	@Size(max = 10000, message = "Application comment cannot exceed 10000 characters")
	private String comment;
	
	@NotNull(message = "Resume flag is required")
	private Boolean resumeFlag;
	
	private Date appliedTime;
	private Date acceptedTime;
	private Date declinedTime;
	
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
	
	public String getStatus(){
		return status;
	}
	
	public void setStatus(String status){
		this.status = status;
	}
	
	public String getComment(){
		return comment;
	}
	
	public void setComment(String comment){
		this.comment = comment;
	}
	
	public Boolean getResumeFlag(){
		return resumeFlag;
	}
	
	public void setResumeFlag(Boolean resumeFlag){
		this.resumeFlag = resumeFlag;
	}
	
	public Date getAppliedTime(){
		return this.appliedTime;
	}
	
	public void setAppliedTime(Date appliedTime){
		this.appliedTime = appliedTime;
	}
	
	public Date getAcceptedTime(){
		return this.acceptedTime;
	}
	
	public void setAcceptedTime(Date acceptedTime){
		this.acceptedTime = acceptedTime;
	}
	
	public Date getDeclinedTime(){
		return this.declinedTime;
	}
	
	public void setDeclinedTime(Date declinedTime){
		this.declinedTime = declinedTime;
	}

}
