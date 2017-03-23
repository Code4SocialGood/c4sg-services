package org.c4sg.service.slack;

import java.util.List;

import org.c4sg.dto.slack.User;

public interface SlackClientService {
	
	void shutdown();
	
	List<User> getUserList();
	Boolean isJoinedChat(String email);
}
