package org.c4sg.constant.slack;

public enum SlackWebApiConstants {

	USERS_LIST("users.list");
	
	private String value;
	
	SlackWebApiConstants(String value) {
		this.value=value;
	}
	
	public String getValue() {
		return value;
	}

}