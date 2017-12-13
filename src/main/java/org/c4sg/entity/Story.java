package org.c4sg.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.sql.Timestamp;

@Entity
@Table(name = "story")
public class Story {

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
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

	public Timestamp getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Timestamp createdTime) {
		this.createdTime = createdTime;
	}

	public UserOrganization getUserOrganization() {
		return userOrganization;
	}

	public void setUserOrganization(UserOrganization userOrganization) {
		this.userOrganization = userOrganization;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", nullable = false, updatable = false)
	private Integer id;

	@Column(name = "image", nullable = false)
	private String image;

	@Column(name = "title")
	private String title;
    
	@Column(name = "body")
	private String body;

	@Column(name = "created_time", nullable = false)
	private Timestamp createdTime;

	@OneToOne
	@JoinColumn(name = "id", updatable = false)
	private UserOrganization userOrganization;
}
