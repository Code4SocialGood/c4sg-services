package org.c4sg.service.impl;

import org.c4sg.service.AsyncEmailService;
import org.c4sg.service.EmailTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import com.sendgrid.*;

import java.io.IOException;
import java.util.Map;

@Service
public class AsyncEmailServiceImpl implements AsyncEmailService {
	
	@Autowired
	private EmailTemplateService service;
		
	@Value("${sendgrid_api_key}")
	private String sendgridApiKey;
	
    /**
     * Sends an email message asynchronously through SendGrid. 
	 * Status Code: 202	- ACCEPTED: Your message is both valid, and queued to be delivered.
     * @param from       email address from which the message will be sent.
     * @param recipient  array of strings containing the recipients of the message.
     * @param subject    subject header field.
     * @param text       content of the message.
     */
    @Async
    public void send(String from, String recipient, String replyTo, String subject, String text) {
    	
		Email emailFrom = new Email(from);
		String emailSubject = subject;
		Email emailTo = new Email(recipient);
		
		Content emailContent = new Content("text/html", text);
		Mail mail = new Mail(emailFrom, emailSubject, emailTo, emailContent);
		if(!replyTo.isEmpty()) {
			Email emailReplyTo = new Email(replyTo);
			mail.setReplyTo(emailReplyTo);
		}
		
		SendGrid sg = new SendGrid(sendgridApiKey);		
		Request request = new Request();
		try {
			request.setMethod(Method.POST);
			request.setEndpoint("mail/send");
			request.setBody(mail.build());
			Response response = sg.api(request);
			System.out.println("Status Code = " + response.getStatusCode());
			System.out.println("BODY = " + response.getBody());
			System.out.println("HEADERS = " + response.getHeaders());
		} catch (IOException ex) {
			ex.printStackTrace();
		}
    }

	@Override
	public void sendWithContext(String from, String recipient, String replyTo, String subject, String template, Map<String, Object> mailContext) {

		String text = service.generateFromContext(mailContext, template);
		send(from, recipient, replyTo, subject, text);
	}  
}

