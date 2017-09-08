package org.c4sg.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class CreateProjectDTO {
	
	@NotNull(message = "Project name is required")
	@Size(max = 100, message = "Project name cannot exceed 100 characters")
	private String name;//required
	
	@NotNull
	private Integer organizationId;//required
	
	@Size(max = 10000, message = "Project description cannot exceed 10000 characters")
	private String description;
	
	@Pattern(regexp="[YN]")
	private String remoteFlag="N";// possible values Y or N 
	
	@Size(max = 100, message = "Address 1 cannot exceed 100 characters")
	private String address1;
	
	@Size(max = 100, message = "Address 2 cannot exceed 100 characters")
	private String address2;
	
	@Size(max = 100, message = "City cannot exceed 100 characters")
	private String city;
	
	@Size(max = 100, message = "State cannot exceed 100 characters")
	private String state;
	
	@Size(max = 100, message = "Country cannot exceed 100 characters")
	private String country;
	
	@Size(max = 100, message = "Zip cannot exceed 100 characters")
	private String zip;
		
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getOrganizationId() {
		return organizationId;
	}

	public void setOrganizationId(Integer organizationId) {
		this.organizationId = organizationId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getRemoteFlag() {
		return remoteFlag;
	}

	public void setRemoteFlag(String remoteFlag) {
		this.remoteFlag = remoteFlag;
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

}
