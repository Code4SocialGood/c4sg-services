package org.c4sg.service.slack.method;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.c4sg.constant.slack.Problem;
import org.c4sg.exception.slack.ValidationError;

public abstract class AbstractMethod implements SlackMethod {

	@Override
	public boolean isRequiredToken() {
		return true;
	}

	@Override
	public abstract String getMethodName();

	@Override
	public abstract void validate(List<ValidationError> errors);

	public void addError(List<ValidationError> errors, String field, Problem problem) {
		addError(errors, field, problem, null);
	}

	public void addError(List<ValidationError> errors, String field, Problem problem, String description) {
		errors.add(new ValidationError(field, problem, description));
	}

	protected abstract void createParameters(Map<String, String> parameters);

	@Override
	public Map<String, String> getParameters() {
		Map<String, String> parameters = new HashMap<String, String>();
		createParameters(parameters);
		return parameters;
	}

}