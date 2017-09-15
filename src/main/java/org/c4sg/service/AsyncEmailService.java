package org.c4sg.service;

import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public interface AsyncEmailService {
    
	void send(String from, String recipient, String replyTo, String subject, String text);

	/**
	 * Sends email using variable automatically inserted into 
	 * @param from
	 * @param recipient
	 * @param replyTo
	 * @param template
	 * @param mailContext
	 */
	void sendWithContext(String from, String recipient, String replyTo, String subject, String template, Map<String, Object> mailContext);
}
