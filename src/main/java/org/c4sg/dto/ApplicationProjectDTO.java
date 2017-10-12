package org.c4sg.dto;

import java.util.Date;

public class ApplicationProjectDTO {
	
	
	private Integer projectId;	
	private String projectName;
	private Integer userId;	
	private String applicationStatus;	
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
	
	public String getProjectName(){
		return projectName;
	}
	
	public void setProjectName(String projectName){
		this.projectName = projectName;
	}
	
	public Integer getUserId(){
		return userId;
	}
	
	public void setUserId(Integer userId){
		this.userId = userId;
	}
	
	public String getApplicationStatus(){
		return applicationStatus;
	}
	
	public void setApplicationStatus(String applicationStatus){
		this.applicationStatus = applicationStatus;
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
	
	public Date getAppliedTime() {
		return appliedTime;
	}
	
	public void setAppliedTime(Date appliedTime) {
		this.appliedTime = appliedTime;
	}
	
	public Date getAcceptedTime() {
		return acceptedTime;
	}
	
	public void setAcceptedTime(Date acceptedTime) {
		this.acceptedTime = acceptedTime;
	}

	public Date getDeclinedTime() {
		return declinedTime;
	} 
	
	public void setDeclinedTime(Date declinedTime) {
		this.declinedTime = declinedTime;
	}
}