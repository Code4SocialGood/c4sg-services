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
    private String publicProfileFlag;
    private String latitude;
    private String longitude;
    private String introduction;
    private String linkedinUrl;
    private String personalUrl;
    private String skill1;
    private String skill2;
    private String skill3;
    private String skill4;
    private String skill5;
    private String skill6;
    private String developerFlag; 

    public String getDeveloperFlag(){
    	return this.developerFlag;
    }
    
    public void setDeveloperFlag(String developerFlag){
    	this.developerFlag = developerFlag;
    }

    public String getIntroduction() {
		return introduction;
	}

	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}

	public String getLinked_inurl() {
		return linkedinUrl;
	}

	public void setLinked_inurl(String linked_inurl) {
		this.linkedinUrl = linked_inurl;
	}

	public String getPersonal_web_site() {
		return personalUrl;
	}

	public void setPersonal_web_site(String personal_web_site) {
		this.personalUrl = personal_web_site;
	}

	public String getSkill1() {
		return skill1;
	}

	public void setSkill1(String skill1) {
		this.skill1 = skill1;
	}

	public String getSkill2() {
		return skill2;
	}

	public void setSkill2(String skill2) {
		this.skill2 = skill2;
	}

	public String getSkill3() {
		return skill3;
	}

	public void setSkill3(String skill3) {
		this.skill3 = skill3;
	}

	public String getSkill4() {
		return skill4;
	}

	public void setSkill4(String skill4) {
		this.skill4 = skill4;
	}

	public String getSkill5() {
		return skill5;
	}

	public void setSkill5(String skill5) {
		this.skill5 = skill5;
	}

	public String getSkill6() {
		return skill6;
	}

	public void setSkill6(String skill6) {
		this.skill6 = skill6;
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

    public String getDisplayFlag() {
        return publicProfileFlag;
    }

    public void setDisplayFlag(String displayFlag) {
        this.publicProfileFlag = displayFlag;
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
