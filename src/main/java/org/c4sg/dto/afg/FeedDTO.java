package org.c4sg.dto.afg;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import org.c4sg.dto.OrganizationDTO;
import org.c4sg.dto.ProjectDTO;

@XmlRootElement(name = "FootprintFeed")
@XmlAccessorType(XmlAccessType.FIELD)
public class FeedDTO {
	
	@XmlAttribute(name="schemaVersion")
	private String schemaVersion = "0.1"; 
	
	@XmlElement(name = "FeedInfo")
	private FeedInfoDTO feedInfo;

	@XmlElementWrapper(name="Organizations")
    @XmlElement(name="Organization")
	private List<OrganizationDTO> organizations;
	
	@XmlElementWrapper(name="VolunteerOpportunities")
    @XmlElement(name="VolunteerOpportunity")
	private List<ProjectDTO> projects;
	
	public List<ProjectDTO> getProjects() {
		return projects;
	}

	public void setProjects(List<ProjectDTO> projects) {
		this.projects = projects;
	}

	public FeedInfoDTO getFeedInfo() {
		return feedInfo;
	}

	public void setFeedInfo(FeedInfoDTO feedInfo) {
		this.feedInfo = feedInfo;
	}

	public List<OrganizationDTO> getOrganizations() {
		return organizations;
	}

	public void setOrganizations(List<OrganizationDTO> organizations) {
		this.organizations = organizations;
	}
	
	public String getSchemaVersion() {
		return schemaVersion;
	}

}
