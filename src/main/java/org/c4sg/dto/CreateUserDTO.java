package org.c4sg.dto;

public class CreateUserDTO {
    
    private String userName;
    private String firstName;
    private String lastName;
    private String email;
    private String state;
    private String country;
    private String phone;
    private String title;
    private String introduction;
    private String avatarUrl;
    private String linkedInUrl;
    private String personalUrl;
    private String githubUrl;
    private String resumeUrl;
    private String facebookUrl;
    private String twitterUrl;
    private String publishFlag="N";
    private String notifyFlag="N";
    private String chatUsername;
    private String role;
    
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
    
    public void setChatUsernameg(String chatUsername) {
        this.chatUsername = chatUsername;
    }    
}
