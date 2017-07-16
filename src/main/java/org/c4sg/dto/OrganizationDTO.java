package org.c4sg.dto;

import java.util.List;

import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;

import org.c4sg.dto.afg.FeedLocationDTO;
import org.hibernate.validator.constraints.Length;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel
@XmlAccessorType(XmlAccessType.FIELD)
public class OrganizationDTO {
	
	@XmlElement(name="organizationId")
	private Integer id;

	@NotNull
	private String name;

	@XmlElement(name="organizationURL")
	private String websiteUrl;
	
	@XmlElement(name="logoURL")
    private String logoUrl;

	@Length(max = 500)
	private String description;

	@XmlTransient
	private String address1;

	@XmlTransient
	private String address2;

	@XmlTransient
	private String city;
	
	@XmlTransient
	private String state;

	@XmlTransient
	private String country;

	@XmlTransient
	private String zip;

	private String contactName;
	
	private String contactTitle;
	
	private String contactPhone;

	private String contactEmail;
		
	@XmlTransient
	private String category;
	
	@XmlTransient
	private String status;

	@XmlTransient
	private String createdTime;
	
	@XmlTransient
	private String projectUpdatedTime;
	
	@XmlTransient
	private List<ProjectDTO> projects;

	public FeedLocationDTO getLocation() {
		return location;
	}

	public void setLocation(FeedLocationDTO location) {
		this.location = location;
	}

	public List<ProjectDTO> getProjects() {
		return projects;
	}

	public void setProjects(List<ProjectDTO> projects) {
		this.projects = projects;
	}

	@XmlElement(name="location")
	private FeedLocationDTO location;
	
	@ApiModelProperty(value = "Organization ID")
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getWebsiteUrl() {
		return websiteUrl;
	}

	public void setWebsiteUrl(String websiteUrl) {
		this.websiteUrl = websiteUrl;
	}
	
	public String getLogoUrl() {
		return logoUrl;
	}
	public void setLogoUrl(String logoUrl) {
		this.logoUrl = logoUrl;
	}
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getAddress1() {
		return address1;
	}

	public void setAddress1(String address1) {
		this.address1 = address1;
	}

	public String getAddress2() {
		return address2;
	}

	public void setAddress2(String address2) {
		this.address2 = address2;
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

	public String getContactName() {
		return contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	public String getContactTitle() {
		return contactTitle;
	}

	public void setContactTitle(String contactTitle) {
		this.contactTitle = contactTitle;
	}
	
	public String getContactPhone() {
		return contactPhone;
	}

	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}

	public String getContactEmail() {
		return contactEmail;
	}

	public void setContactEmail(String contactEmail) {
		this.contactEmail = contactEmail;
	}

	@ApiModelProperty(value = "N-nonprofit, O-opensource")
	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}
	
	@ApiModelProperty(value = "A-active")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(String createdTime) {
		this.createdTime = createdTime;
	}

	public String getProjectUpdatedTime() {
		return projectUpdatedTime;
	}

	public void setProjectUpdatedTime(String projectUpdatedTime) {
		this.projectUpdatedTime = projectUpdatedTime;
	}
}