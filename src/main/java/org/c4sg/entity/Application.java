package org.c4sg.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "application")
public class Application implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, updatable = false)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;

    @Column(name = "comment")
    private String comment;
    
    @Column(name="resume_flag", nullable=false, columnDefinition="char(1)")
    private String resumeflag;
    
    @Basic
    @Column(name="status", columnDefinition="char(1)", nullable = false)
    private String status;
    
    @Column(name = "applied_time")
	private Date appliedTime;
    
    @Column(name = "accepted_time")
	private Date acceptedTime;
    
    @Column(name = "declined_time")
	private Date declinedTime;
    
	@Column(name = "created_time", nullable = false)
	private Date createdTime;
		
	@Column(name = "updated_time", nullable = false)
	private Date updatedTime;
	
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }
    
    public String getComment(){
    	return comment;
    }
    
    public void setComment(String comment){
    	this.comment = comment;
    }
    
    public String getResumeFlag(){
    	return resumeflag;
    }
    
    public void setResumeFlag(String resumeFlag){
    	this.resumeflag = resumeFlag;
    }
    
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
    public Date getAppliedTime(){
    	return appliedTime;
    }
    
    public void setAppliedTime(Date appliedTime){
    	this.appliedTime = appliedTime;
    }
    
    public Date getAcceptedTime(){
    	return acceptedTime;
    }
    
    public void setAcceptedTime(Date acceptedTime){
    	this.acceptedTime = acceptedTime;
    }
    
    public Date getDeclinedTime(){
    	return declinedTime;
    }
    
    public void setDeclinedTime(Date declinedTime){
    	this.declinedTime = declinedTime;
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
