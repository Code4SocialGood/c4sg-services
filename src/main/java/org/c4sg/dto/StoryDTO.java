package org.c4sg.dto;

public class StoryDTO {

	private int id;

	private String imageUrl;
	private String title;
	private String body;
	private String volunteerOrganizationName;
	private String volunteerOrganizationUrl;

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}


	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getVolunteerOrganizationName() {
		return volunteerOrganizationName;
	}

	public void setVolunteerOrganizationName(String volunteerOrganizationName) {
		this.volunteerOrganizationName = volunteerOrganizationName;
	}

	public String getVolunteerOrganizationUrl() {
		return volunteerOrganizationUrl;
	}

	public void setVolunteerOrganizationUrl(String volunteerOrganizationUrl) {
		this.volunteerOrganizationUrl = volunteerOrganizationUrl;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}
