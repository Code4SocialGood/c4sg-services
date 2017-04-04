package org.c4sg.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.c4sg.constant.UserStatus;
import org.c4sg.constant.UserRole;
import org.c4sg.converter.StatusConverter;
import org.c4sg.converter.UserRoleConverter;

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
    
    @Column(name = "phone")
    private String phone;
    
    @Column(name = "city")
    private String city;
    
    @Column(name = "state")
    private String state;
    
    @Column(name = "country")
    private String country;
    
    @Column(name = "zip")
    private String zip;
    
    @Column(name = "introduction")
    private String introduction;
    
    @Column(name = "linkedin_url")
    private String linkedinUrl;
    
    @Column(name = "personal_url")
    private String personalUrl;   

    @Convert(converter = UserRoleConverter.class)
    @Column(name = "role", columnDefinition="char(1)", nullable = false)
    private UserRole role;

    @Column(name = "public_profile_flag", columnDefinition="char(1)", nullable = false)
    private String publicProfileFlag;

    @Column(name = "chat_flag", columnDefinition="char(1)", nullable = false)
    private String chatFlag;
            
    @Column(name = "forum_flag", columnDefinition="char(1)", nullable = false)
    private String forumFlag;

    @Column(name="developer_flag", columnDefinition="char(1)", nullable = false)
    private String developerFlag;
  
    @Convert(converter = StatusConverter.class)
    @Column(name = "status", columnDefinition="char(1)", nullable = false)
    private UserStatus status;
     
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

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
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

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	public String getIntroduction() {
		return introduction;
	}

	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}

	public String getLinkedinUrl() {
		return linkedinUrl;
	}

	public void setLinkedinUrl(String linkedinUrl) {
		this.linkedinUrl = linkedinUrl;
	}

	public String getPersonalUrl() {
		return personalUrl;
	}

	public void setPersonalUrl(String personalUrl) {
		this.personalUrl = personalUrl;
	}

	public UserRole getRole() {
		return role;
	}

	public void setRole(UserRole role) {
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

	public String getDeveloperFlag() {
		return developerFlag;
	}

	public void setDeveloperFlag(String developerFlag) {
		this.developerFlag = developerFlag;
	}

	public UserStatus getStatus() {
		return status;
	}

	public void setStatus(UserStatus status) {
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
