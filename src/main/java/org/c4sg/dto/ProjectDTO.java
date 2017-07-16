package org.c4sg.dto;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlTransient;

import org.c4sg.dto.afg.FeedDateTimeDurationDTO;
import org.c4sg.dto.afg.FeedSponsoringOrganizationDTO;

@XmlAccessorType(XmlAccessType.FIELD)
public class ProjectDTO {

	@XmlElement(name="volunteerOpportunityID")
	private int id;
	
	@XmlElement(name="title")
	private String name;	
	
	@XmlTransient
	private String organizationId;	
	
	private String description;	
    
	@XmlTransient
	private String imageUrl;
    
	@XmlTransient
	private String state;
    
	@XmlTransient
	private String country;
    
	@XmlTransient
	private String remoteFlag;
    
	//TODO: We need to change remoteFlag to have values of true or false.
    private String virtual;
	
	@XmlTransient
    private String status;	
	
	@XmlTransient
	private String createdTime;	
    
    @XmlElement(name="lastUpdated")
    private String updatedTime;
	
	@XmlTransient
	private String organizationName;
	
	@XmlElementWrapper(name="dateTimeDurations")
    @XmlElement(name="dateTimeDuration")
    private List<FeedDateTimeDurationDTO> dateTimeDurations;
	
	@XmlElementWrapper(name="sponsoringOrganizationIDs")
    @XmlElement(name="sponsoringOrganizationID")
	private List<FeedSponsoringOrganizationDTO> sponsoringIds;
	
	public List<FeedSponsoringOrganizationDTO> getSponsoringIds() {
		return sponsoringIds;
	}

	public void setSponsoringIds(List<FeedSponsoringOrganizationDTO> sponsoringIds) {
		this.sponsoringIds = sponsoringIds;
	}

	public List<FeedDateTimeDurationDTO> getDateTimeDurations() {
		return dateTimeDurations;
	}

	public void setDateTimeDurations(List<FeedDateTimeDurationDTO> dateTimeDurations) {
		this.dateTimeDurations = dateTimeDurations;
	}

	public String getVirtual() {
		return virtual;
	}

	public void setVirtual(String virtual) {
		this.virtual = virtual;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getOrganizationId() {
		return organizationId;
	}

	public void setOrganizationId(String organizationId) {
		this.organizationId = organizationId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getImageUrl() {
		return imageUrl;
	}
	
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
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
	
	public String getRemoteFlag() {
		return remoteFlag;
	}

	public void setRemoteFlag(String remoteFlag) {
		this.remoteFlag = remoteFlag;
	}
	
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
	
	public String getUpdatedTime() {
		return updatedTime;
	}

	public void setUpdatedTime(String updatedTime) {
		this.updatedTime = updatedTime;
	}

	public String getOrganizationName() {
		return organizationName;
	}

	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
	}
}
