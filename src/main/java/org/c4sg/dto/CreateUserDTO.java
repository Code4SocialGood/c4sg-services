package org.c4sg.dto;

import javax.validation.constraints.Size;

public class CreateUserDTO {
    
	@Size(max = 100, message = "User Name cannot exceed 100 characters")
    private String userName;
	
	@Size(max = 100, message = "First Name cannot exceed 100 characters")
    private String firstName;
	
	@Size(max = 100, message = "Last Name cannot exceed 100 characters")
    private String lastName;
	
	@Size(max = 100, message = "Email cannot exceed 100 characters")
	private String email;
    
	@Size(max = 100, message = "State cannot exceed 100 characters")
    private String state;
	
	@Size(max = 100, message = "Country cannot exceed 100 characters")
    private String country;
	
	@Size(max = 30, message = "Phone cannot exceed 100 characters")
    private String phone;
	
	@Size(max = 100, message = "Title cannot exceed 100 characters")
    private String title;
    
	@Size(max = 10000, message = "User introduction cannot exceed 10000 characters")
    private String introduction;
	
	@Size(max = 150, message = "Avatar URL cannot exceed 150 characters")
    private String avatarUrl;
	
	@Size(max = 150, message = "LinkedIn URL cannot exceed 150 characters")
    private String linkedInUrl;
	
	@Size(max = 150, message = "Personal URL cannot exceed 150 characters")
    private String personalUrl;
	
	@Size(max = 100, message = "Github URL cannot exceed 150 characters")
    private String githubUrl;
	    
	@Size(max = 25, message = "Chat Username cannot exceed 25 characters")
    private String chatUsername;
	
    private String role;
    
    private String publishFlag = "Y";
    private String notifyFlag = "Y";
    
    // Following fields are not used
    private String resumeUrl;
    private String facebookUrl;
    private String twitterUrl;
    
    public String getUserName() {
        return userName;
    }
    
    public void setUserName(String userName) {
        this.userName = userName;
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
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getPhone() {
        return phone;
    }
    
    public void setPhone(String phone) {
        this.phone = phone;
    }
    
    public String getState() {
        return state;
    }
    
    public void setState(String state) {
        this.state = state;
    }
    
    public String getCountry() {
        return country;
    }
    
    public void setCountry(String country) {
        this.country = country;
    }
    
    public String getTitle() {
        return title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    public String getIntroduction() {
        return introduction;
    }
    
    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }
    
    public String getLinkedInUrl() {
        return linkedInUrl;
    }
    
    public void setLinkedInUrl(String linkedInUrl) {
        this.linkedInUrl = linkedInUrl;
    }
    
    public String getPersonalUrl() {
        return personalUrl;
    }
    
    public void setPersonalUrl(String personalUrl) {
        this.personalUrl = personalUrl;
    }
    
    public String getGithubUrl() {
        return githubUrl;
    }
    
    public void setGithubUrl(String githubUrl) {
        this.githubUrl = githubUrl;
    }
    
    public String getAvatarUrl() {
        return avatarUrl;
    }
    
    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }
    
    public String getFacebookUrl() {
        return facebookUrl;
    }
    
    public void setFacebookUrl(String facebookUrl) {
        this.resumeUrl = facebookUrl;
    }
    
    public String getTwitterUrl() {
        return twitterUrl;
    }
    
    public void setTwitterUrl(String twitterUrl) {
        this.twitterUrl = twitterUrl;
    }
    
    public String getResumeUrl() {
        return resumeUrl;
    }
    
    public void setResumeUrl(String resumeUrl) {
        this.resumeUrl = resumeUrl;
    }
    
    public String getRole() {
        return role;
    }
    
    public void setRole(String role) {
        this.role = role;
    }
    
    public String getPublishFlag() {
        return publishFlag;
    }
    
    public void setPublishFlag(String publishFlag) {
        this.publishFlag = publishFlag;
    }
    
    public String getNotifyFlag() {
        return notifyFlag;
    }
    
    public void setNotifyFlag(String notifyFlag) {
        this.notifyFlag = notifyFlag;
    }
    
    public String getChatUsername() {
        return chatUsername;
    }
    
    public void setChatUsername(String chatUsername) {
        this.chatUsername = chatUsername;
    }    
}
