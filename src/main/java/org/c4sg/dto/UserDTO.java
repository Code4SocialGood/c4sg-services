package org.c4sg.dto;

public class UserDTO {
    private Integer id;
    private String userName;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String state;
    private String country;
    private String zip;
    private String status;
    private String role;
    private Integer github;
    private String displayFlag;
    private String latitude;
    private String longitude;
    private String introduction;
    private String linked_inurl;
    private String personal_web_site;
    private String resume;

    private char developerFlag; 


    public char getDeveloperFlag(){
    	return this.developerFlag;
    }
    
    public void setDeveloperFlag(char developerFlag){
    	this.developerFlag = developerFlag;
    }

    public String getIntroduction() {
		return introduction;
	}

	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}

	public String getLinked_inurl() {
		return linked_inurl;
	}

	public void setLinked_inurl(String linked_inurl) {
		this.linked_inurl = linked_inurl;
	}

	public String getPersonal_web_site() {
		return personal_web_site;
	}

	public void setPersonal_web_site(String personal_web_site) {
		this.personal_web_site = personal_web_site;
	}

	public String getResume() {
		return resume;
	}

	public void setResume(String resume) {
		this.resume = resume;
	}

	
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Integer getGithub() {
        return github;
    }

    public void setGithub(Integer github) {
        this.github = github;
    }

    public String getDisplayFlag() {
        return displayFlag;
    }

    public void setDisplayFlag(String displayFlag) {
        this.displayFlag = displayFlag;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

}
