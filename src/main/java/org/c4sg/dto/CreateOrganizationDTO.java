package org.c4sg.dto;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel
public class CreateOrganizationDTO {
	
	@NotBlank
	@Size(max = 100, message = "Organization name cannot exceed 100 characters")
	private String name;
	
	@Size(max = 100, message = "Website URL name cannot exceed 100 characters")
	private String websiteUrl;
	
	@Size(max = 10000, message = "Organization description cannot exceed 10000 characters")
	private String description;
	
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
	
	@Size(max = 16, message = "Zip Code cannot exceed 16 characters")
	private String zip;

	private String category;
	
	@Size(max = 10, message = "EINe cannot exceed 10 characters")
	private String ein;

	// The following fields are not used	
	private String contactName;	
	private String contactTitle;
	private String contactPhone;
	private String contactEmail;
		
	@ApiModelProperty(required = true)
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

	@ApiModelProperty(value = "N-nonprofit, O-opensource, default to N")
	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}
	
	public String getEin() {
		return ein;
	}

	public void setEin(String ein) {
		this.ein = ein;
	}

}
