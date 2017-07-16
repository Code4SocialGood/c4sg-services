package org.c4sg.dto.afg;

public class FeedDateTimeDurationDTO {
	
	private String openEnded = "true";
	
	private String duration = "3 months";
	
	private String timeFlexible = "true";
	
	private String commitmentHoursPerWeek = "5";
	
	public String getOpenEnded() {
		return openEnded;
	}
	public void setOpenEnded(String openEnded) {
		this.openEnded = openEnded;
	}
	public String getDuration() {
		return duration;
	}
	public void setDuration(String duration) {
		this.duration = duration;
	}
	public String getTimeFlexible() {
		return timeFlexible;
	}
	public void setTimeFlexible(String timeFlexible) {
		this.timeFlexible = timeFlexible;
	}
	public String getCommitmentHoursPerWeek() {
		return commitmentHoursPerWeek;
	}
	public void setCommitmentHoursPerWeek(String commitmentHoursPerWeek) {
		this.commitmentHoursPerWeek = commitmentHoursPerWeek;
	}
	
	
}
