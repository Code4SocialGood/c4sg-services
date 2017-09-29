package org.c4sg.dto;

public class ApplicationDTO {
	
	private String status;
	private String comment;
	private String resumeFlag;
	
	public String getStatus(){
		return status;
	}
	
	public void setStatus(String status){
		this.status = status;
	}
	
	public String getComment(){
		return comment;
	}
	
	public void setComment(String comment){
		this.comment = comment;
	}
	
	public String getResumeFlag(){
		return resumeFlag;
	}
	
	public void setResumeFlag(String resumeFlag){
		this.resumeFlag = resumeFlag;
	}

}
