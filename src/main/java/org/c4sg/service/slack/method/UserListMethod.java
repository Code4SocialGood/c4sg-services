package org.c4sg.service.slack.method;

import java.util.List;
import java.util.Map;

import org.c4sg.constant.slack.SlackWebApiConstants;
import org.c4sg.exception.slack.ValidationError;

public class UserListMethod extends AbstractMethod {

	public UserListMethod() {
	}

	public UserListMethod(String presence) {
		this.presence = presence;
	}

	protected String presence;

	public String getPresence() {
		return presence;
	}

	public void setPresence(String presence) {
		this.presence = presence;
	}

	@Override
	public String getMethodName() {
		return SlackWebApiConstants.USERS_LIST.getValue();
	}

	@Override
	public void validate(List<ValidationError> errors) {
		// ignore
	}

	@Override
	protected void createParameters(Map<String, String> parameters) {
		if (presence != null) {
			parameters.put("presence", presence);
		}
	}

}