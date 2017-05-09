package org.c4sg.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "user")
public class User implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 2014_04_17_001L;

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	
    @Column(name = "id", nullable = false, updatable = false)
    private Integer id;
	
    @Column(name = "username")
    private String userName;
    
    @Column(name = "first_name")
    private String firstName;
    
    @Column(name = "last_name")
    private String lastName;
    
    @Column(name = "email", nullable = false)
    private String email;
    
    @Column(name = "state")
    private String state;
    
    @Column(name = "country")
    private String country;
    
    @Column(name = "latitude")
    private BigDecimal latitude;
    
    @Column(name = "longitude")
    private BigDecimal longitude;
    
	@Column(name = "title")
	private String title;	
	
    @Column(name = "introduction")
    private String introduction;
    
    @Column(name = "avatar_url")
    private String avatarUrl;
    
    @Column(name = "linkedin_url")
    private String linkedinUrl;
    
    @Column(name = "personal_url")
    private String personalUrl;   
    
	@Column(name = "github_url")
	private String gitHubUrl;
	
    @Column(name = "resume_url")
    private String resumeUrl;
    
    @Column(name = "facebook_url")
    private String facebookUrl;
    
    @Column(name = "twitter_url")
    private String twitterUrl;

    @Column(name = "role", columnDefinition="char(1)", nullable = false)
    private String role;

    @Column(name = "public_profile_flag", columnDefinition="char(1)", nullable = false)
    private String publicProfileFlag;

    @Column(name = "chat_flag", columnDefinition="char(1)", nullable = false)
    private String chatFlag;
            
    @Column(name = "forum_flag", columnDefinition="char(1)", nullable = false)
    private String forumFlag;

    @Column(name = "status", columnDefinition="char(1)", nullable = false)
    private String status;
     
	@Column(name = "created_time")
	private Date createdTime;
		
	@Column(name = "updated_time")
	private Date updatedTime;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

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
	
	public BigDecimal getLatitude() {
		return latitude;
	}

	public void setLatitude(BigDecimal latitude) {
		this.latitude = latitude;
	}
	
	public BigDecimal getLongitude() {
		return longitude;
	}

	public void setLongitude(BigDecimal longitude) {
		this.longitude = longitude;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title= title;
	}
	
	public String getIntroduction() {
		return introduction;
	}

	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}

	public String getAvatarUrl() {
		return avatarUrl;
	}

	public void setAvatarUrl(String avatarUrl) {
		this.avatarUrl = avatarUrl;
	}
	
	public String getLinkedinUrl() {
		return linkedinUrl;
	}

	public void setLinkedinUrl(String linkedinUrl) {
		this.linkedinUrl = linkedinUrl;
	}
	
	public String getGitHubUrl() {
		return gitHubUrl;
	}

	public void setGitHubUrl(String gitHubUrl) {
		this.gitHubUrl = gitHubUrl;
	}

	public String getPersonalUrl() {
		return personalUrl;
	}

	public void setPersonalUrl(String personalUrl) {
		this.personalUrl = personalUrl;
	}

	public String getResumeUrl() {
		return resumeUrl;
	}

	public void setResumeUrl(String resumeUrl) {
		this.resumeUrl = resumeUrl;
	}
	
	public String getFacebookUrl() {
		return facebookUrl;
	}

	public void setFacebookUrl(String facebookUrl) {
		this.facebookUrl = facebookUrl;
	}

	public String getTwitterUrl() {
		return twitterUrl;
	}

	public void setTwitterUrl(String twitterUrl) {
		this.twitterUrl = twitterUrl;
	}
	
	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getPublicProfileFlag() {
		return publicProfileFlag;
	}

	public void setPublicProfileFlag(String publicProfileFlag) {
		this.publicProfileFlag = publicProfileFlag;
	}

	public String getChatFlag() {
		return chatFlag;
	}

	public void setChatFlag(String chatFlag) {
		this.chatFlag = chatFlag;
	}

	public String getForumFlag() {
		return forumFlag;
	}

	public void setForumFlag(String forumFlag) {
		this.forumFlag = forumFlag;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

	public Date getUpdatedTime() {
		return updatedTime;
	}

	public void setUpdatedTime(Date updatedTime) {
		this.updatedTime = updatedTime;
	}
	
}
