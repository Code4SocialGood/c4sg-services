package org.c4sg.service.slack.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.c4sg.constant.slack.Problem;
import org.c4sg.dao.UserDAO;
import org.c4sg.dto.slack.Profile;
import org.c4sg.dto.slack.User;
import org.c4sg.dto.slack.channel.Group;
import org.c4sg.exception.slack.SlackArgumentException;
import org.c4sg.exception.slack.SlackException;
import org.c4sg.exception.slack.SlackResponseErrorException;
import org.c4sg.exception.slack.ValidationError;
import org.c4sg.service.slack.SlackClientService;
import org.c4sg.service.slack.method.CreatePrivateChannel;
import org.c4sg.service.slack.method.SlackMethod;
import org.c4sg.service.slack.method.UserListMethod;
import org.c4sg.util.slack.SlackUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class SlackClientServiceImpl implements SlackClientService {

    @Value("${slack.auth.token}")
    private String slackAuthToken;

    @Value("${slack.webapi.url}")
    private String slackApiUrl;

    @Value("${slack.webapi.document.url}")
    private String slackApiDocUrl;

    @Value("${slack.default.timeout}")
    private String slackDefaultTimeOut;

    private ObjectMapper mapper;
    private CloseableHttpClient httpClient;

    @Autowired
    private UserDAO userDAO;

    @Override
    public void shutdown() {
        if (httpClient != null)
            try {
                httpClient.close();
            } catch (Exception e) {
            }
    }

    @Override
    public List<User> getUserList() {
        JsonNode retNode = call(new UserListMethod());
        return readValue(retNode, "members", new TypeReference<List<User>>() {
        });
    }

    @Override
    public Boolean isJoinedChat(String email) {
        //token=SlackWebApiConstants.SLACK_AUTH_TOKEN;
        httpClient = SlackUtils.createHttpClient(Integer.parseInt(slackDefaultTimeOut));
        mapper = new ObjectMapper();
        try {
            org.c4sg.entity.User user = userDAO.findByEmail(email);
            if (user == null) {
                return false;
            }
            if (user.getChatUsername() == null) {
                List<User> slackUsers = getUserList();
                for (User slackUser : slackUsers) {
                    Profile slackUserProfile = slackUser.getProfile();
                    if (slackUserProfile != null && slackUserProfile.getEmail() != null && slackUserProfile.getEmail().equalsIgnoreCase(email)) {
                        // userDAO.updateIsSlackRegisteredFlag("Y", user.getId());
                        return true;
                    }
                }
                // userDAO.updateIsSlackRegisteredFlag("N", user.getId());
                return false;
            } else {
                return true;
            }
        } finally {
            shutdown();
        }

    }

    @Override
    public Group createPrivateChannel(String channelName) {
        httpClient = SlackUtils.createHttpClient(Integer.parseInt(slackDefaultTimeOut));
        mapper = new ObjectMapper();
        return getPrivateChannel(channelName);

    }


    public Group getPrivateChannel(String channelName) {
        CreatePrivateChannel c = new CreatePrivateChannel();
        c.setChannelName(channelName);
        JsonNode retNode = call(c);
        return readValue(retNode, "group", new TypeReference<Group>() {
        });
    }


    protected <T> T readValue(JsonNode node, String findPath, Class<T> valueType) {
        try {
            if (findPath != null) {
                if (!node.has(findPath))
                    return null;
                node = node.findPath(findPath);
            }
            return mapper.readValue(node.toString(), valueType);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    protected <T> T readValue(JsonNode node, String findPath, TypeReference<?> typeReference) {
        try {
            if (findPath != null) {
                if (!node.has(findPath))
                    return null;
                node = node.findPath(findPath);
            }
            return mapper.readValue(node.toString(), typeReference);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    protected JsonNode call(SlackMethod method) {
        return call(method, null);
    }

    protected JsonNode call(SlackMethod method, InputStream is) {

        List<ValidationError> errors = new ArrayList<>();
        method.validate(errors);

        if (errors.size() > 0) {

            StringBuffer sb = new StringBuffer("*** slack argument error ***");
            for (ValidationError error : errors) {
                if (sb.length() > 0) {
                    sb.append("\n");
                }

                if (error.getDescription() != null) {
                    sb.append(error.getDescription());
                } else if (error.getProblem() == Problem.REQUIRED) {
                    sb.append("\"" + error.getField() + "\" is required.");
                }
            }

            throw new SlackArgumentException(sb.toString());
        }

        Map<String, String> parameters = method.getParameters();
        if (method.isRequiredToken()) {
            parameters.put("token", slackAuthToken);
        }

        String apiUrl = slackApiUrl + "/" + method.getMethodName();

        HttpEntity httpEntity = null;
        if (is == null) {
            httpEntity = SlackUtils.createUrlEncodedFormEntity(parameters);
        } else {
            httpEntity = SlackUtils.createMultipartFormEntity(parameters, is);
        }

        String retContent = SlackUtils.execute(httpClient, apiUrl, httpEntity);

        JsonNode retNode = null;
        try {
            retNode = mapper.readTree(retContent);
        } catch (IOException e) {
            throw new SlackException(e);
        }

        boolean retOk = retNode.findPath("ok").asBoolean();
        if (!retOk) {
            String error = retNode.findPath("error").asText();
            throw new SlackResponseErrorException(error + ". check the link "
                    + slackApiDocUrl + "/" + method.getMethodName());
        }

        return retNode;
    }

}
