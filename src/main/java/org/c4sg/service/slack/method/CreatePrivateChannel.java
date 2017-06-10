package org.c4sg.service.slack.method;

import org.c4sg.constant.slack.SlackWebApiConstants;
import org.c4sg.exception.slack.ValidationError;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CreatePrivateChannel extends AbstractMethod {

    public CreatePrivateChannel() {
    }

    protected String channelName;

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    @Override
    public String getMethodName() {
        return SlackWebApiConstants.GROUPS_CREATE.getValue();
    }

    @Override
    public void validate(List<ValidationError> errors) {
        // ignore
    }

    @Override
    protected void createParameters(Map<String, String> parameters) {
        parameters.put("name", channelName);
    }

    @Override
    public Map<String, String> getParameters() {
        Map<String, String> parameters = new HashMap<>();
        createParameters(parameters);
        return parameters;
    }

}