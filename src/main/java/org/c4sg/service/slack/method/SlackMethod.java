package org.c4sg.service.slack.method;

import java.util.List;
import java.util.Map;

import org.c4sg.exception.slack.ValidationError;


public interface SlackMethod {

	boolean isRequiredToken();

	String getMethodName();

	void validate(List<ValidationError> errors);

	Map<String, String> getParameters();

}