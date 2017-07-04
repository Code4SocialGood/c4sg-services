package org.c4sg.dto;

public class ApplicantDTO {
	
    private Integer userId;
    private Integer projectId;
    private String firstName;
    private String lastName;
    private String title;
    private String applicationStatus;
    private String appliedTime;
    private String acceptedTime;
    private String declinedTime;

	public Integer getUserId() {
		return userId;
	}
	
	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getProjectId() {
		return projectId;
	}
	
	public void setProjectId(Integer projectId) {
		this.projectId = projectId;
	}
	public String getFirstName() {
		return firstName;
	}
	
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	public String getLastName() {
		return lastName;
	}
	
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getApplicationStatus() {
		return applicationStatus;
	}
	
	public void setApplicationStatus(String applicationStatus) {
		this.applicationStatus = applicationStatus;
	}
	
	public String getAppliedTime() {
		return appliedTime;
	}
	
	public void setAppliedTime(String appliedTime) {
		this.appliedTime = appliedTime;
	}
	
	public String getAcceptedTime() {
		return acceptedTime;
	}
	
	public void setAcceptedTime(String acceptedTime) {
		this.acceptedTime = acceptedTime;
	}

	public String getDeclinedTime() {
		return declinedTime;
	} 
	
	public void setDeclinedTime(String declinedTime) {
		this.declinedTime = declinedTime;
	}
}
