package org.c4sg.dto;

import java.util.Date;

public class ApplicationDTO {
	
	private Integer projectId;
	private Integer userId;
	private String status;
	private String comment;
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
